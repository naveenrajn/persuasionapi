package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

public class RuleComparator implements java.io.Serializable {

	private String comparatorId;
	private String description;
	private Set ruleComparisons = new HashSet(0);

	public RuleComparator() {
	}

	public RuleComparator(String comparatorId, String description) {
		this.comparatorId = comparatorId;
		this.description = description;
	}

	public RuleComparator(String comparatorId, String description,
			Set ruleComparisons) {
		this.comparatorId = comparatorId;
		this.description = description;
		this.ruleComparisons = ruleComparisons;
	}

	public String getComparatorId() {
		return this.comparatorId;
	}

	public void setComparatorId(String comparatorId) {
		this.comparatorId = comparatorId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getRuleComparisons() {
		return this.ruleComparisons;
	}

	public void setRuleComparisons(Set ruleComparisons) {
		this.ruleComparisons = ruleComparisons;
	}

}
