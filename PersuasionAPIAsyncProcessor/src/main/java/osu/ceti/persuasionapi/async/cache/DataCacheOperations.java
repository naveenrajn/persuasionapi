package osu.ceti.persuasionapi.async.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.data.access.BadgeRuleMappingsDAO;
import osu.ceti.persuasionapi.data.access.BadgeRulesDAO;
import osu.ceti.persuasionapi.data.model.BadgeRuleMappings;
import osu.ceti.persuasionapi.data.model.BadgeRules;

@Service
public class DataCacheOperations {
	
	private static final Log log = LogFactory.getLog(DataCacheOperations.class);
	
	@Autowired BadgeRuleMappingsDAO badgeRuleMappingsDAO;
	@Autowired BadgeRulesDAO badgeRulesDAO;
	
	/**
	 * Populates the current set of badge processing rules to the cache
	 * @throws PersuasionAPIException 
	 */
	@Transactional
	public void populateOrRefreshBadgeRules() throws PersuasionAPIException {
		List<BadgeRuleMappings> badgeRuleMappings = badgeRuleMappingsDAO.getAllBadgeRuleMappings();
		
		Map<String, Map<Integer, BadgeRuleMappings>> badgeRuleMappingsMap = new HashMap<String, Map<Integer,BadgeRuleMappings>>();
		Map<BadgeRuleMappings, Map<Integer, List<BadgeRules>>> badgeRulesMap = new HashMap<BadgeRuleMappings, Map<Integer,List<BadgeRules>>>();
		
		for(BadgeRuleMappings badgeRuleMapping : badgeRuleMappings) {
			String badgeClass = badgeRuleMapping.getBadges().getBadgeClass();
			if(!badgeRuleMappingsMap.containsKey(badgeClass)) {
				badgeRuleMappingsMap.put(badgeClass, new TreeMap<Integer, BadgeRuleMappings>(Collections.reverseOrder()));
			}
			badgeRuleMappingsMap.get(badgeClass).put(badgeRuleMapping.getRuleOrder(), badgeRuleMapping);
			
			BadgeRules searchExample = new BadgeRules();
			searchExample.setBadgeRuleMappings(badgeRuleMapping);
			log.debug("Finding rules for rule ID: " + badgeRuleMapping.getRuleId());
			List<BadgeRules> badgeRules = badgeRulesDAO.findByRuleId(badgeRuleMapping.getRuleId());
			Map<Integer, List<BadgeRules>> rulesGroupedByGroupId = new HashMap<Integer, List<BadgeRules>>();
			for(BadgeRules badgeRule : badgeRules) {
				int groupId = badgeRule.getComparisonGroup();
				if(!rulesGroupedByGroupId.containsKey(groupId)) {
					rulesGroupedByGroupId.put(groupId, new ArrayList<BadgeRules>());
				}
				rulesGroupedByGroupId.get(groupId).add(badgeRule);
			}
			log.debug("Found " + badgeRules.size() + " rules containing " + rulesGroupedByGroupId.size() + " groups");
			badgeRulesMap.put(badgeRuleMapping, rulesGroupedByGroupId);
		}
		
		DataCache.badgeRuleMappings = badgeRuleMappingsMap;
		DataCache.badgeRules = badgeRulesMap;
	}
	
	/**
	 * Checks the cache for badge processing rules. If not available, populates them
	 * @throws PersuasionAPIException
	 */
	public void checkOrPopulateBadgeRules() throws PersuasionAPIException {
		if(DataCache.badgeRuleMappings == null || DataCache.badgeRules == null) {
			populateOrRefreshBadgeRules();
		}
	}
}
