package osu.ceti.persuasionapi.data.model;

import java.util.HashSet;
import java.util.Set;

public class Activity implements java.io.Serializable {

	private String activityName;
	private String activityDesc;
	private Set ruleComparisons = new HashSet(0);
	private Set activityLogs = new HashSet(0);

	public Activity() {
	}

	public Activity(String activityName) {
		this.activityName = activityName;
	}

	public Activity(String activityName, String activityDesc,
			Set ruleComparisons, Set activityLogs) {
		this.activityName = activityName;
		this.activityDesc = activityDesc;
		this.ruleComparisons = ruleComparisons;
		this.activityLogs = activityLogs;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDesc() {
		return this.activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Set getRuleComparisons() {
		return this.ruleComparisons;
	}

	public void setRuleComparisons(Set ruleComparisons) {
		this.ruleComparisons = ruleComparisons;
	}

	public Set getActivityLogs() {
		return this.activityLogs;
	}

	public void setActivityLogs(Set activityLogs) {
		this.activityLogs = activityLogs;
	}

}
