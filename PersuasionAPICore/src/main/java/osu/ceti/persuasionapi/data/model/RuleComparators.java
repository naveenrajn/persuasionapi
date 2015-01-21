package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Maps to persuasion_api.rule_comparators
 */
public class RuleComparators implements java.io.Serializable {

	private String comparatorId;
	private String description;
	private Set badgeRuleses = new HashSet(0);

	public RuleComparators() {
	}

	public RuleComparators(String comparatorId, String description) {
		this.comparatorId = comparatorId;
		this.description = description;
	}

	public RuleComparators(String comparatorId, String description,
			Set badgeRuleses) {
		this.comparatorId = comparatorId;
		this.description = description;
		this.badgeRuleses = badgeRuleses;
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

	public Set getBadgeRuleses() {
		return this.badgeRuleses;
	}

	public void setBadgeRuleses(Set badgeRuleses) {
		this.badgeRuleses = badgeRuleses;
	}

}
