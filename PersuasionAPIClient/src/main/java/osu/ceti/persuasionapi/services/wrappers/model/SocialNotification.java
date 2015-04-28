package osu.ceti.persuasionapi.services.wrappers.model;

import java.util.Date;

import osu.ceti.persuasionapi.core.helpers.CustomDateDeserializer;
import osu.ceti.persuasionapi.core.helpers.CustomDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SocialNotification {

	String notificationText;
	Date timestamp;

	public String getNotificationText() {
		return notificationText;
	}
	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getTimestamp() {
		return timestamp;
	}
	
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
