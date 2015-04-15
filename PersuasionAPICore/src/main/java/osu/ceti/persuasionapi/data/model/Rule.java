package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

public class Rule implements java.io.Serializable {

	private Integer ruleId;
	private Rule parentRule;
	private String ruleName;
	private String ruleDesc;
	private Set ruleComparisons = new HashSet(0);
	private Set childRules = new HashSet(0);
	private Set ruleQueueMappings = new HashSet(0);
	private RuleAction ruleAction;

	public Rule() {
	}

	public Rule(String ruleName) {
		this.ruleName = ruleName;
	}

	public Rule(Rule parentRule, String ruleName, String ruleDesc,
			Set ruleComparisons, Set childRules, Set ruleQueueMappings,
			RuleAction ruleAction) {
		this.parentRule = parentRule;
		this.ruleName = ruleName;
		this.ruleDesc = ruleDesc;
		this.ruleComparisons = ruleComparisons;
		this.childRules = childRules;
		this.ruleQueueMappings = ruleQueueMappings;
		this.ruleAction = ruleAction;
	}

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Rule getParentRule() {
		return this.parentRule;
	}

	public void setParentRule(Rule rule) {
		this.parentRule = rule;
	}

	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDesc() {
		return this.ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public Set getRuleComparisons() {
		return this.ruleComparisons;
	}

	public void setRuleComparisons(Set ruleComparisons) {
		this.ruleComparisons = ruleComparisons;
	}

	public Set getChildRules() {
		return this.childRules;
	}

	public void setChildRules(Set rules) {
		this.childRules = rules;
	}

	public Set getRuleQueueMappings() {
		return this.ruleQueueMappings;
	}

	public void setRuleQueueMappings(Set ruleQueueMappings) {
		this.ruleQueueMappings = ruleQueueMappings;
	}

	public RuleAction getRuleAction() {
		return this.ruleAction;
	}

	public void setRuleAction(RuleAction ruleAction) {
		this.ruleAction = ruleAction;
	}

}
