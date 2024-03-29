package osu.ceti.persuasionapi.data.model;

public class RuleQueueMappingId implements java.io.Serializable {

	private Rule rule;
	private String queueName;

	public RuleQueueMappingId() {
	}

	public RuleQueueMappingId(Rule rule, String queueName) {
		this.rule = rule;
		this.queueName = queueName;
	}

	public Rule getRule() {
		return this.rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public String getQueueName() {
		return this.queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RuleQueueMappingId))
			return false;
		RuleQueueMappingId castOther = (RuleQueueMappingId) other;

		return ((this.getRule() == castOther.getRule()) || (this.getRule() != null
				&& castOther.getRule() != null && this.getRule().equals(
				castOther.getRule())))
				&& ((this.getQueueName() == castOther.getQueueName()) || (this
						.getQueueName() != null
						&& castOther.getQueueName() != null && this
						.getQueueName().equals(castOther.getQueueName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRule() == null ? 0 : this.getRule().hashCode());
		result = 37 * result
				+ (getQueueName() == null ? 0 : this.getQueueName().hashCode());
		return result;
	}

}
