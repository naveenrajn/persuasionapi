package osu.ceti.persuasionapi.services.internal.wrappers;

public class BadgeResponse {

	String badgeId;
	String badgeClass;
	String badgeLevel;
	String badgeName;
	String badgeDesc;
	String emailSubject;
	String emailMsg;
	String publicRecognition;

	public String getBadgeId() {
		return badgeId;
	}
	public void setBadgeId(String badgeId) {
		this.badgeId = badgeId;
	}
	public String getBadgeClass() {
		return badgeClass;
	}
	public void setBadgeClass(String badgeClass) {
		this.badgeClass = badgeClass;
	}
	public String getBadgeLevel() {
		return badgeLevel;
	}
	public void setBadgeLevel(String badgeLevel) {
		this.badgeLevel = badgeLevel;
	}
	public String getBadgeName() {
		return badgeName;
	}
	public void setBadgeName(String badgeName) {
		this.badgeName = badgeName;
	}
	public String getBadgeDesc() {
		return badgeDesc;
	}
	public void setBadgeDesc(String badgeDesc) {
		this.badgeDesc = badgeDesc;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getEmailMsg() {
		return emailMsg;
	}
	public void setEmailMsg(String emailMsg) {
		this.emailMsg = emailMsg;
	}
	public String getPublicRecognition() {
		return publicRecognition;
	}
	public void setPublicRecognition(String publicRecognition) {
		this.publicRecognition = publicRecognition;
	}
}
