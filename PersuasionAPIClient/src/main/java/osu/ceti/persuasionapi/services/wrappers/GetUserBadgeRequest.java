package osu.ceti.persuasionapi.services.wrappers;

public class GetUserBadgeRequest {
	
	String userId;
	String badgeClass;
	
	public GetUserBadgeRequest() {
		//Dummy Constructor to allow JSON to POJO conversion
	}
	
	public GetUserBadgeRequest(String userId, String badgeClass) {
		this.userId = userId;
		this.badgeClass = badgeClass;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBadgeClass() {
		return badgeClass;
	}

	public void setBadgeClass(String badgeClass) {
		this.badgeClass = badgeClass;
	}
	
}
