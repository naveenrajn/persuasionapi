package osu.ceti.persuasionapi.async.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.async.cache.DataCacheOperations;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.operations.ActivityLogOperations;
import osu.ceti.persuasionapi.core.operations.ActivityOperations;
import osu.ceti.persuasionapi.core.operations.UserOperations;

@Service
@Transactional
public class RuleCacheUpdateTriggerService {
	
	private static final Log log = LogFactory.getLog(RuleCacheUpdateTriggerService.class);	
	
	@Autowired DataCacheOperations dataCacheOperations;
	@Autowired UserOperations userOperations;
	@Autowired ActivityOperations activityOperations;
	@Autowired ActivityLogOperations activityLogOperations;

	/**
	 * Triggers update of rule cache
	 */
	public void updateRuleCache() {
		try {
			dataCacheOperations.populateOrRefreshBadgeRules();
		} catch (PersuasionAPIException e) {
			log.error("Failed to update badge rule cache in PersuasionAPIAsyncProcessor");
		}
	}

}
