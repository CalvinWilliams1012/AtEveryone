package calvin.williams;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * GroupMe application to mention everyone in the group when a user types @
 * everyone. This class is the Servlet that processes the POST/GET the GroupMe
 * API sends to my url.
 * 
 * @author Calvin A. Williams
 *
 */
public class AtEveryoneEngine extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Base url is the api url provided by GroupMe.
	 */
	static final String BaseUrl = "https://api.groupme.com/v3";
	/**
	 * endUrl is used to end all GET/POST's to the GroupMe API. This String
	 * contains my API key.
	 */
	static final String urlEnd = "?token=" + accessToken.token;

	/**
	 * Servlet method called on HTTP GET,sent to handle method.
	 * 
	 * @param request
	 *            The HTTP GET request and information.
	 * @param response
	 *            The response to the GET request.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		handle(request, response);
	}

	/**
	 * Servlet method called on HTTP POST, sent to handle method. This is what
	 * is called when a new message is entered into the chat.
	 * 
	 * @param request
	 *            The HTTP POST request with the message JSON.
	 * @param response
	 *            The reponse to the HTTP POST.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		handle(request, response);
	}

	/**
	 * Called from both doGet and doPost. Converts request from JSON to
	 * MessageR, if the message contains '@everyone' then the Group is grabbed
	 * and a message is POST'ed to the group that mentions all users.
	 * 
	 * @param req
	 *            The HTTP Request.
	 * @param resp
	 *            The HTTP response.
	 * @throws IOException
	 */
	public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		MessageR msg = gson.fromJson(buildJson(req.getReader()), MessageR.class);

		if (msg.containsAtEveryone()) {
			Group grp = getGroup(msg.getGroupID());
			if (grp != null) {
				AtEveryone(grp);
			}
		}
	}

	public String buildJson(BufferedReader r) throws IOException {
		StringBuilder buffer = new StringBuilder();
		String line = null;
		while ((line = r.readLine()) != null) {
			buffer.append(line);
		}
		/*
		 * Groupme-API has a bad way of handling things were sometimes the JSON
		 * starts with {"response": {--stuff I want returned}
		 * "meta":{--httpresponsecode--}} where all I really want is the
		 * content, so this if is to remove that.
		 */
		if (buffer.substring(0, 11).contains("response")) {
			buffer.delete(0, 12);
			buffer.delete(buffer.length() - 22, buffer.length() - 1);
		}
		return buffer.toString();
	}

	/**
	 * Called when the '@everyone' String is found in the message. Creates a
	 * HTTP GET request for the JSON GROUP then casts it to the Group and
	 * returns it.
	 * 
	 * @param group_id
	 *            The ID of the group.
	 * @return Group object from HTTP GET
	 * @throws IOException
	 */
	public Group getGroup(String group_id) throws IOException {
		String groupUrl = "/groups/" + group_id;
		URL url = new URL(AtEveryoneEngine.BaseUrl + groupUrl + AtEveryoneEngine.urlEnd);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		int responsecode = conn.getResponseCode();
		if (responsecode == HttpURLConnection.HTTP_OK) {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			Group group = gson.fromJson(buildJson(new BufferedReader(new InputStreamReader(conn.getInputStream()))),
					Group.class);
			return group;
		} else {
			return null;
		}
	}

	/**
	 * Send a message in the group that mentions all users in the provided
	 * group.
	 * 
	 * @param grp
	 *            provided group.
	 */
	public void AtEveryone(Group grp) throws IOException {
		Gson gson = new Gson();
		String botPost = "/bots/post";
		URL url = new URL(AtEveryoneEngine.BaseUrl + botPost + AtEveryoneEngine.urlEnd);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");

		List<User> members = grp.getMembers();
		String nicknames = "";
		JsonArray uids = new JsonArray();
		JsonArray locis = new JsonArray();
		int place = 0;
		for (int i = 0; i < members.size(); i++) {
			uids.add(members.get(i).getUser_ID());
			JsonArray temp = new JsonArray();
			temp.add(place);
			place += members.get(i).getNickname().length();
			temp.add(place);
			locis.add(temp);
			nicknames += "@" + members.get(i).getNickname() + " ";

		}

		JsonObject post = new JsonObject();
		post.add("bot_id", new JsonPrimitive(accessToken.botID));
		post.add("text", new JsonPrimitive(nicknames));

		JsonObject innerObj = new JsonObject();

		innerObj.add("type", new JsonPrimitive("mentions"));
		innerObj.add("user_ids", uids);
		innerObj.add("loci", locis);
		JsonArray inner = new JsonArray();
		inner.add(innerObj);
		post.add("attachments",inner);
		
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		String temp = post.toString();
		System.out.println(temp);
		wr.writeBytes(temp);
		wr.flush();
		wr.close();

		int responsecode = conn.getResponseCode();
		if (responsecode == HttpURLConnection.HTTP_CREATED) {

		}
	}

}