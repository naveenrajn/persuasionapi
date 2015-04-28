package osu.ceti.persuasionapi.services.internal.wrappers;

public class RuleComparisonsWrapper {

	String ruleComparisonId;
	String featureType;
	String activityOrAttributeName;
	String ruleComparator;
	String value;
	
	public String getRuleComparisonId() {
		return ruleComparisonId;
	}
	public void setRuleComparisonId(String ruleComparisonId) {
		this.ruleComparisonId = ruleComparisonId;
	}
	public String getFeatureType() {
		return featureType;
	}
	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}
	public String getActivityOrAttributeName() {
		return activityOrAttributeName;
	}
	public void setActivityOrAttributeName(String activityOrAttributeName) {
		this.activityOrAttributeName = activityOrAttributeName;
	}
	public String getRuleComparator() {
		return ruleComparator;
	}
	public void setRuleComparator(String ruleComparator) {
		this.ruleComparator = ruleComparator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
