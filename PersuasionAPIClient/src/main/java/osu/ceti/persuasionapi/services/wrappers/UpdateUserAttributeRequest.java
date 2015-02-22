package osu.ceti.persuasionapi.services.wrappers;

import java.util.List;

import osu.ceti.persuasionapi.services.wrappers.model.UserAttribute;

public class UpdateUserAttributeRequest {
	
	private String userId;
	private List<UserAttribute> attributes;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<UserAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<UserAttribute> attributes) {
		this.attributes = attributes;
	}
}
