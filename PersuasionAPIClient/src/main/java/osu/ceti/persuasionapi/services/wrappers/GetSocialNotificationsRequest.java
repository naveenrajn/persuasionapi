package osu.ceti.persuasionapi.services.wrappers;

public class GetSocialNotificationsRequest {

	String userId;
	String timestamp;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
