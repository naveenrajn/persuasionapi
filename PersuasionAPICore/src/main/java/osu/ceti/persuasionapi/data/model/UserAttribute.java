package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

public class UserAttribute implements java.io.Serializable {

	private String attributeName;
	private String attributeDesc;
	private Set ruleComparisons = new HashSet(0);
	private Set userAttributeValues = new HashSet(0);

	public UserAttribute() {
	}

	public UserAttribute(String attributeName) {
		this.attributeName = attributeName;
	}

	public UserAttribute(String attributeName, String attributeDesc,
			Set ruleComparisons, Set userAttributeValues) {
		this.attributeName = attributeName;
		this.attributeDesc = attributeDesc;
		this.ruleComparisons = ruleComparisons;
		this.userAttributeValues = userAttributeValues;
	}

	public String getAttributeName() {
		return this.attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeDesc() {
		return this.attributeDesc;
	}

	public void setAttributeDesc(String attributeDesc) {
		this.attributeDesc = attributeDesc;
	}

	public Set getRuleComparisons() {
		return this.ruleComparisons;
	}

	public void setRuleComparisons(Set ruleComparisons) {
		this.ruleComparisons = ruleComparisons;
	}

	public Set getUserAttributeValues() {
		return this.userAttributeValues;
	}

	public void setUserAttributeValues(Set userAttributeValues) {
		this.userAttributeValues = userAttributeValues;
	}

}
