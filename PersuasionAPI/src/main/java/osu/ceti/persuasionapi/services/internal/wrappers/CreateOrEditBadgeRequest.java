package osu.ceti.persuasionapi.services.internal.wrappers;

public class CreateOrEditBadgeRequest {
	
	boolean newBadgeClass;
	String badgeId;
	String badgeClass;
	String badgeLevel;
	String badgeName;
	String badgeDescription;
	String emailSubject;
	String emailBody;
	String socialUpdate;
	
	public String getBadgeId() {
		return badgeId;
	}
	public void setBadgeId(String badgeId) {
		this.badgeId = badgeId;
	}
	public boolean isNewBadgeClass() {
		return newBadgeClass;
	}
	public void setNewBadgeClass(boolean newBadgeClass) {
		this.newBadgeClass = newBadgeClass;
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
	public String getBadgeDescription() {
		return badgeDescription;
	}
	public void setBadgeDescription(String badgeDescription) {
		this.badgeDescription = badgeDescription;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public String getSocialUpdate() {
		return socialUpdate;
	}
	public void setSocialUpdate(String socialUpdate) {
		this.socialUpdate = socialUpdate;
	}
}
