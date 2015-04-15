package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

public class Badge implements java.io.Serializable {

	private Integer badgeId;
	private String badgeClass;
	private int badgeLevel;
	private String badgeName;
	private String badgeDesc;
	private byte[] image;
	private String emailSubject;
	private String emailMsg;
	private String publicRecognition;
	private Set userBadgeMappings = new HashSet(0);
	private Set ruleActions = new HashSet(0);

	public Badge() {
	}

	public Badge(String badgeClass, int badgeLevel, String badgeName) {
		this.badgeClass = badgeClass;
		this.badgeLevel = badgeLevel;
		this.badgeName = badgeName;
	}

	public Badge(String badgeClass, int badgeLevel, String badgeName,
			String badgeDesc, byte[] image, String emailSubject,
			String emailMsg, String publicRecognition, Set userBadgeMappings,
			Set ruleActions) {
		this.badgeClass = badgeClass;
		this.badgeLevel = badgeLevel;
		this.badgeName = badgeName;
		this.badgeDesc = badgeDesc;
		this.image = image;
		this.emailSubject = emailSubject;
		this.emailMsg = emailMsg;
		this.publicRecognition = publicRecognition;
		this.userBadgeMappings = userBadgeMappings;
		this.ruleActions = ruleActions;
	}

	public Integer getBadgeId() {
		return this.badgeId;
	}

	public void setBadgeId(Integer badgeId) {
		this.badgeId = badgeId;
	}

	public String getBadgeClass() {
		return this.badgeClass;
	}

	public void setBadgeClass(String badgeClass) {
		this.badgeClass = badgeClass;
	}

	public int getBadgeLevel() {
		return this.badgeLevel;
	}

	public void setBadgeLevel(int badgeLevel) {
		this.badgeLevel = badgeLevel;
	}

	public String getBadgeName() {
		return this.badgeName;
	}

	public void setBadgeName(String badgeName) {
		this.badgeName = badgeName;
	}

	public String getBadgeDesc() {
		return this.badgeDesc;
	}

	public void setBadgeDesc(String badgeDesc) {
		this.badgeDesc = badgeDesc;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getEmailSubject() {
		return this.emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailMsg() {
		return this.emailMsg;
	}

	public void setEmailMsg(String emailMsg) {
		this.emailMsg = emailMsg;
	}

	public String getPublicRecognition() {
		return this.publicRecognition;
	}

	public void setPublicRecognition(String publicRecognition) {
		this.publicRecognition = publicRecognition;
	}

	public Set getUserBadgeMappings() {
		return this.userBadgeMappings;
	}

	public void setUserBadgeMappings(Set userBadgeMappings) {
		this.userBadgeMappings = userBadgeMappings;
	}

	public Set getRuleActions() {
		return this.ruleActions;
	}

	public void setRuleActions(Set ruleActions) {
		this.ruleActions = ruleActions;
	}

}
