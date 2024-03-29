package osu.ceti.persuasionapi.data.model;

import java.util.Date;

public class UserAttributeValue implements java.io.Serializable {

	private UserAttributeValueId id;
	private String value;
	private Date logTime;

	public UserAttributeValue() {
	}

	public UserAttributeValue(UserAttributeValueId id, String value,
			Date logTime) {
		this.id = id;
		this.value = value;
		this.logTime = logTime;
	}

	public UserAttributeValueId getId() {
		return this.id;
	}

	public void setId(UserAttributeValueId id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getLogTime() {
		return this.logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

}
