package osu.ceti.persuasionapi.services.wrappers;

public class GetSocialNotificationsRequest {

	String userId;
	String timeStamp;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
