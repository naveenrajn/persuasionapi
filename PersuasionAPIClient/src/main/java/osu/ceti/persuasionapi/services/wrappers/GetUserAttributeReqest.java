package osu.ceti.persuasionapi.services.wrappers;

public class GetUserAttributeReqest {

	String userId;
	String attributeName;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
}
