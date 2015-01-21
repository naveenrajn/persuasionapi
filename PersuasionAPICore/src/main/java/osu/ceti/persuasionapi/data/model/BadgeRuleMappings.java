package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Maps to persuasion_api.badge_rule_mappings
 */
public class BadgeRuleMappings implements java.io.Serializable {

	private Integer ruleId;
	private Badges badges;
	private String ruleName;
	private String ruleDesc;
	private int ruleOrder;
	private Set badgeRuleses = new HashSet(0);

	public BadgeRuleMappings() {
	}

	public BadgeRuleMappings(String ruleName, int ruleOrder) {
		this.ruleName = ruleName;
		this.ruleOrder = ruleOrder;
	}

	public BadgeRuleMappings(Badges badges, String ruleName, String ruleDesc,
			int ruleOrder, Set badgeRuleses) {
		this.badges = badges;
		this.ruleName = ruleName;
		this.ruleDesc = ruleDesc;
		this.ruleOrder = ruleOrder;
		this.badgeRuleses = badgeRuleses;
	}

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Badges getBadges() {
		return this.badges;
	}

	public void setBadges(Badges badges) {
		this.badges = badges;
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

	public int getRuleOrder() {
		return this.ruleOrder;
	}

	public void setRuleOrder(int ruleOrder) {
		this.ruleOrder = ruleOrder;
	}

	public Set getBadgeRuleses() {
		return this.badgeRuleses;
	}

	public void setBadgeRuleses(Set badgeRuleses) {
		this.badgeRuleses = badgeRuleses;
	}

}
