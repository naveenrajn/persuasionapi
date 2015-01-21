package osu.ceti.persuasionapi.async.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.async.cache.DataCacheOperations;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.operations.ActivityLogOperations;
import osu.ceti.persuasionapi.core.operations.ActivityOperations;
import osu.ceti.persuasionapi.core.operations.UserOperations;

@Controller
@RequestMapping("/triggers")
public class RuleCacheUpdateTrigger {
	
	private static final Log log = LogFactory.getLog(RuleCacheUpdateTrigger.class);	

	@Autowired DataCacheOperations dataCacheOperations;
	@Autowired UserOperations userOperations;
	@Autowired ActivityOperations activityOperations;
	@Autowired ActivityLogOperations activityLogOperations;
	
	/**
	 * Refreshes the badge rules cache
	 * To be called by the UI to update the cache after updating badge rules
	 */
	@RequestMapping(value="/updatecache", method=RequestMethod.GET)
	@ResponseBody
	public void updateRuleCache() {
		try {
			dataCacheOperations.populateOrRefreshBadgeRules();
		} catch (PersuasionAPIException e) {
			log.error("Failed to update badge rule cache in PersuasionAPIAsyncProcessor");
		}
	}
	
}
