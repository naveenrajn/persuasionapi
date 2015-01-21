package osu.ceti.persuasionapi.async.cache;

import java.util.List;
import java.util.Map;

import osu.ceti.persuasionapi.data.model.BadgeRuleMappings;
import osu.ceti.persuasionapi.data.model.BadgeRules;

public class DataCache {
	
	//TODO: Add comments as to what these mean
	public static Map<String, Map<Integer, BadgeRuleMappings>> badgeRuleMappings;
	public static Map<BadgeRuleMappings, Map<Integer, List<BadgeRules>>> badgeRules;
	
}
