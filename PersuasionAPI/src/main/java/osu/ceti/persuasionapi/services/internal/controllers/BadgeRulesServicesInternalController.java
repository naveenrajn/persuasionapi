package osu.ceti.persuasionapi.services.internal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;

@Controller
@RequestMapping("/badges")
public class BadgeRulesServicesInternalController {

	@RequestMapping(value="/createrule", method=RequestMethod.POST)
	@ResponseBody
	public void createBadgeRuleMapping() {
		
	}
	
	public void updateBadgeRuleMappings(RestServiceRequest request) {
		
	}
	
}
