package osu.ceti.persuasionapi.services.wrappers;

public class ReportActivityRequest {

	private String userId;
	private String activityName;
	private String value;
	
	public ReportActivityRequest() {
		//Dummy Constructor to allow JSON to POJO conversion
	}
	
	public ReportActivityRequest(String userId, String activityName, String value) {
		this.userId = userId;
		this.activityName = activityName;
		this.value = value;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
