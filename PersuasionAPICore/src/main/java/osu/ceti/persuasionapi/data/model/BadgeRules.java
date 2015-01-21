package osu.ceti.persuasionapi.data.model;

/**
 * Maps to persuasion_api.badge_rules
 */
public class BadgeRules implements java.io.Serializable {

	private Integer ruleCompId;
	private BadgeRuleMappings badgeRuleMappings;
	private RuleComparators ruleComparators;
	private Integer activityId;
	private String value;
	private int comparisonGroup;

	public BadgeRules() {
	}

	public BadgeRules(String value, int comparisonGroup) {
		this.value = value;
		this.comparisonGroup = comparisonGroup;
	}

	public BadgeRules(BadgeRuleMappings badgeRuleMappings,
			RuleComparators ruleComparators, Integer activityId, String value,
			int comparisonGroup) {
		this.badgeRuleMappings = badgeRuleMappings;
		this.ruleComparators = ruleComparators;
		this.activityId = activityId;
		this.value = value;
		this.comparisonGroup = comparisonGroup;
	}

	public Integer getRuleCompId() {
		return this.ruleCompId;
	}

	public void setRuleCompId(Integer ruleCompId) {
		this.ruleCompId = ruleCompId;
	}

	public BadgeRuleMappings getBadgeRuleMappings() {
		return this.badgeRuleMappings;
	}

	public void setBadgeRuleMappings(BadgeRuleMappings badgeRuleMappings) {
		this.badgeRuleMappings = badgeRuleMappings;
	}

	public RuleComparators getRuleComparators() {
		return this.ruleComparators;
	}

	public void setRuleComparators(RuleComparators ruleComparators) {
		this.ruleComparators = ruleComparators;
	}

	public Integer getActivityId() {
		return this.activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getComparisonGroup() {
		return this.comparisonGroup;
	}

	public void setComparisonGroup(int comparisonGroup) {
		this.comparisonGroup = comparisonGroup;
	}

}
