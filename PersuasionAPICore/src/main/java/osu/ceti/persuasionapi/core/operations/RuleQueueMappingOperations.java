package osu.ceti.persuasionapi.core.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.access.RuleQueueMappingDAO;
import osu.ceti.persuasionapi.data.model.RuleQueueMapping;
import osu.ceti.persuasionapi.data.model.RuleQueueMappingId;

@Component
public class RuleQueueMappingOperations {
	
	@Autowired RuleQueueMappingDAO ruleQueueMappingDAO;

	public void updateCustomJMSQueuesForRule(
			osu.ceti.persuasionapi.data.model.Rule rule,
			List<String> ruleQueueMappings) throws DatabaseException {
		if(rule!=null) {
			ruleQueueMappingDAO.deleteAllQueuesForRule(rule.getRuleId());
			for(String queueName : ruleQueueMappings) {
				if(StringHelper.isNotEmpty(queueName)) {
					RuleQueueMappingId id = new RuleQueueMappingId(rule, queueName);
					RuleQueueMapping ruleQueueMapping = new RuleQueueMapping(id);
					ruleQueueMappingDAO.merge(ruleQueueMapping);
				}
			}
		}
	}

	public void deleteAllQueueMappingsForRule(Integer ruleId) throws DatabaseException {
		ruleQueueMappingDAO.deleteAllQueuesForRule(ruleId);
	}
	
	
}
