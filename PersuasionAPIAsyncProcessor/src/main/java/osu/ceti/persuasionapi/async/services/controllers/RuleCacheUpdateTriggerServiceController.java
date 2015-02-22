package osu.ceti.persuasionapi.async.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.async.services.RuleCacheUpdateTriggerService;

@Controller
@RequestMapping("/triggers")
public class RuleCacheUpdateTriggerServiceController {
	
	@Autowired RuleCacheUpdateTriggerService ruleCacheUpdateTriggerService;
	
	/**
	 * Refreshes the badge rules cache
	 * To be called by the UI to update the cache after updating badge rules
	 */
	@RequestMapping(value="/updatecache", method=RequestMethod.GET)
	@ResponseBody
	public void updateRuleCache() {
		ruleCacheUpdateTriggerService.updateRuleCache();
	}
	
}
