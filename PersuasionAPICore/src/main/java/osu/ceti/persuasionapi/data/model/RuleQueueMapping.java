package osu.ceti.persuasionapi.data.model;

public class RuleQueueMapping implements java.io.Serializable {

	private RuleQueueMappingId id;

	public RuleQueueMapping() {
	}

	public RuleQueueMapping(RuleQueueMappingId id) {
		this.id = id;
	}

	public RuleQueueMappingId getId() {
		return this.id;
	}

	public void setId(RuleQueueMappingId id) {
		this.id = id;
	}

}
