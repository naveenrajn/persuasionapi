package osu.ceti.persuasionapi.services.internal.wrappers;

import java.util.ArrayList;
import java.util.List;

public class Rule {
	
	private String ruleId;
	private String parentRuleId;
	private String ruleName;
	private String ruleDesc;
	private String badgeId;
	private String badgeClass;
	private String badgeName;
	private String badgeDescription;
	private String emailSubject;
	private String emailMsg;
	private String socialUpdate;
	private boolean notifyAlways;
	private List<String> ruleQueueMappings = new ArrayList<String>();
	private List<RuleComparisonsWrapper> ruleComparisons = new ArrayList<RuleComparisonsWrapper>();
	
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getParentRuleId() {
		return parentRuleId;
	}
	public void setParentRuleId(String parentRuleId) {
		this.parentRuleId = parentRuleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
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
	public String getEmailMsg() {
		return emailMsg;
	}
	public void setEmailMsg(String emailMsg) {
		this.emailMsg = emailMsg;
	}
	public String getSocialUpdate() {
		return socialUpdate;
	}
	public void setSocialUpdate(String socialUpdate) {
		this.socialUpdate = socialUpdate;
	}
	public boolean isNotifyAlways() {
		return notifyAlways;
	}
	public void setNotifyAlways(boolean notifyAlways) {
		this.notifyAlways = notifyAlways;
	}
	public List<String> getRuleQueueMappings() {
		return ruleQueueMappings;
	}
	public void setRuleQueueMappings(List<String> ruleQueueMappings) {
		this.ruleQueueMappings = ruleQueueMappings;
	}
	public List<RuleComparisonsWrapper> getRuleComparisons() {
		return ruleComparisons;
	}
	public void setRuleComparisons(List<RuleComparisonsWrapper> ruleComparisons) {
		this.ruleComparisons = ruleComparisons;
	}
}
