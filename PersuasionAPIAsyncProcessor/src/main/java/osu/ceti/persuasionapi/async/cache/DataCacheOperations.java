package osu.ceti.persuasionapi.async.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.data.access.RuleDAO;

@Service
public class DataCacheOperations {
	
	@Autowired RuleDAO ruleDAO;
	
	/**
	 * Populates the current set of rules to the cache
	 * @throws PersuasionAPIException 
	 */
	@SuppressWarnings("unchecked")
	public void populateOrRefreshBadgeRules() throws PersuasionAPIException {
		DataCache.rules = ruleDAO.getAllTopLevelRules();
	}
	
	/**
	 * Checks the cache for processing rules. If not available, populates them
	 * @throws PersuasionAPIException
	 */
	public void checkOrPopulateBadgeRules() throws PersuasionAPIException {
		if(DataCache.rules == null) {
			populateOrRefreshBadgeRules();
		}
	}
}
