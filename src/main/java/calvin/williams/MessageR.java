package calvin.williams;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;

/**
 * MessageR is an object for each message sent in the GroupMe chat.
 * Every message will be cast into this object then checked if the message
 * text contains '@everyone' with the inner method.
 * 
 * @author Calvin A. Williams
 *
 */
public class MessageR {
	public MessageR(){}

	@Expose(serialize = false, deserialize = false)
	private transient JsonArray attachments;
	@Expose()
	private String avatar_url;
	@Expose()
	private int created_at;
	@Expose()
	private String group_id;
	@Expose()
	private String id;
	@Expose()
	private String name;
	@Expose()
	private String sender_id;
	@Expose()
	private String sender_type;
	@Expose()
	private String source_guid;
	@Expose()
	private String system;
	@Expose()
	private String text;
	@Expose()
	private String user_id;

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public void setCreated_at(int created_at) {
		this.created_at = created_at;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	public void setSender_type(String sender_type) {
		this.sender_type = sender_type;
	}

	public void setSource_guid(String source_guid) {
		this.source_guid = source_guid;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * Checks if the message text contains '@everyone'.
	 * 
	 * @return true if the message contains '@everyone' false otherwise.
	 */
	public boolean containsAtEveryone() {
		if (text != null) {
			if (text.toLowerCase().contains("@everyone")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * If the message contains '@everyone' the groupID is needed to grab and
	 * message the group.
	 * 
	 * @return group_id
	 */
	public String getGroupID() {
		return group_id;
	}

}
