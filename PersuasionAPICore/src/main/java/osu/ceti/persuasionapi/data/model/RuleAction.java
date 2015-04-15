package osu.ceti.persuasionapi.data.model;

public class RuleAction implements java.io.Serializable {

	private int ruleId;
	private Badge badge;
	private String emailSubject;
	private String emailMsg;
	private String socialUpdate;
	private Boolean notifyAlways;

	public RuleAction() {
	}

	public RuleAction(int ruleId) {
		this.ruleId = ruleId;
	}

	public RuleAction(int ruleId, Badge badge, String emailSubject,
			String emailMsg, String socialUpdate, Boolean notifyAlways) {
		this.ruleId = ruleId;
		this.badge = badge;
		this.emailSubject = emailSubject;
		this.emailMsg = emailMsg;
		this.socialUpdate = socialUpdate;
		this.notifyAlways = notifyAlways;
	}

	public int getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public Badge getBadge() {
		return this.badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
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

	public String getSocialUpdate() {
		return this.socialUpdate;
	}

	public void setSocialUpdate(String socialUpdate) {
		this.socialUpdate = socialUpdate;
	}

	public Boolean getNotifyAlways() {
		return this.notifyAlways;
	}

	public void setNotifyAlways(Boolean notifyAlways) {
		this.notifyAlways = notifyAlways;
	}

}
