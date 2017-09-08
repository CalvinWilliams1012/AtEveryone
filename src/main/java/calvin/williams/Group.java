package calvin.williams;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;

public class Group {
	public Group(){};
	
	@Expose()
	private String id;
	@Expose()
	private String name;
	@Expose()
	private String type;
	@Expose()
	private String description;
	@Expose()
	private String image_url;
	@Expose()
	private String creator_user_id;
	@Expose()
	private int created_at;
	@Expose()
	private int updated_at;
	@Expose()
	private List<User> members;
	@Expose()
	private String share_url;
	@Expose(serialize = false, deserialize = false)
	private transient JsonArray messages;

	public String getID() {
		return id;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public void setCreator_user_id(String creator_user_id) {
		this.creator_user_id = creator_user_id;
	}

	public void setCreated_at(int created_at) {
		this.created_at = created_at;
	}

	public void setUpdated_at(int updated_at) {
		this.updated_at = updated_at;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	public void setMessages(JsonArray messages) {
		this.messages = messages;
	}
}
