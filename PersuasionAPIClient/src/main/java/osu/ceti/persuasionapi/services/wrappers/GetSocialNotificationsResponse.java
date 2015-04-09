package osu.ceti.persuasionapi.services.wrappers;

import java.util.List;

import osu.ceti.persuasionapi.services.wrappers.model.SocialNotification;

public class GetSocialNotificationsResponse {

	String userId;
	List<SocialNotification> notifications;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<SocialNotification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<SocialNotification> notifications) {
		this.notifications = notifications;
	}
	
}
