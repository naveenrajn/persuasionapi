package osu.ceti.persuasionapi.data.model;

import java.util.Date;

public class UserSocialNotification implements java.io.Serializable {

	private Integer notificationId;
	private Badge badge;
	private Rule rule;
	private User user;
	private String notificationText;
	private Date postedTime;

	public UserSocialNotification() {
	}

	public UserSocialNotification(User user, String notificationText,
			Date postedTime) {
		this.user = user;
		this.notificationText = notificationText;
		this.postedTime = postedTime;
	}

	public UserSocialNotification(Badge badge, Rule rule, User user,
			String notificationText, Date postedTime) {
		this.badge = badge;
		this.rule = rule;
		this.user = user;
		this.notificationText = notificationText;
		this.postedTime = postedTime;
	}

	public Integer getNotificationId() {
		return this.notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public Badge getBadge() {
		return this.badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}

	public Rule getRule() {
		return this.rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNotificationText() {
		return this.notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	public Date getPostedTime() {
		return this.postedTime;
	}

	public void setPostedTime(Date postedTime) {
		this.postedTime = postedTime;
	}

}
