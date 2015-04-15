package osu.ceti.persuasionapi.data.model;

public class RuleComparison implements java.io.Serializable {

	private Integer ruleCompId;
	private Activity activity;
	private UserAttribute userAttribute;
	private Rule rule;
	private RuleComparator ruleComparator;
	private String featureType;
	private String value;

	public RuleComparison() {
	}

	public RuleComparison(RuleComparator ruleComparator, String featureType,
			String value) {
		this.ruleComparator = ruleComparator;
		this.featureType = featureType;
		this.value = value;
	}

	public RuleComparison(Activity activity, UserAttribute userAttribute,
			Rule rule, RuleComparator ruleComparator, String featureType,
			String value) {
		this.activity = activity;
		this.userAttribute = userAttribute;
		this.rule = rule;
		this.ruleComparator = ruleComparator;
		this.featureType = featureType;
		this.value = value;
	}

	public Integer getRuleCompId() {
		return this.ruleCompId;
	}

	public void setRuleCompId(Integer ruleCompId) {
		this.ruleCompId = ruleCompId;
	}

	public Activity getActivity() {
		return this.activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public UserAttribute getUserAttribute() {
		return this.userAttribute;
	}

	public void setUserAttribute(UserAttribute userAttribute) {
		this.userAttribute = userAttribute;
	}

	public Rule getRule() {
		return this.rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public RuleComparator getRuleComparator() {
		return this.ruleComparator;
	}

	public void setRuleComparator(RuleComparator ruleComparator) {
		this.ruleComparator = ruleComparator;
	}

	public String getFeatureType() {
		return this.featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
