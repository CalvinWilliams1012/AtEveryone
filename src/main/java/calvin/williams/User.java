package calvin.williams;

import com.google.gson.annotations.Expose;

public class User {
	public User(){};

	@Expose()
	private String user_id;
	@Expose()
	private String nickname;
	@Expose(serialize = false, deserialize = false)
	private transient String muted;
	@Expose(serialize = false, deserialize = false)
	private transient String image_url;

	public String getUser_ID() {
		return user_id;
	}
	
	public String getNickname(){
		return nickname;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setMuted(String muted) {
		this.muted = muted;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

}
