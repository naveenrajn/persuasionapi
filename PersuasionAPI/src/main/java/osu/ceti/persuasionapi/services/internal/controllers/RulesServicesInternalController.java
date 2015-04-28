package osu.ceti.persuasionapi.services.internal.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.services.ControllerTemplate;
import osu.ceti.persuasionapi.services.RestServiceResponse;
import osu.ceti.persuasionapi.services.internal.RulesServicesInternal;
import osu.ceti.persuasionapi.services.internal.wrappers.Rule;
import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;

@Controller
@RequestMapping("/rules")
public class RulesServicesInternalController extends ControllerTemplate {
	
	private static final Logger log = Logger.getLogger(RulesServicesInternalController.class);
	
	@Autowired RulesServicesInternal rulesService;

	@RequestMapping(value="/getAllRules", method=RequestMethod.GET)
	@ResponseBody
	public RestServiceResponse getAllRules() {
		try {
			List<Rule> rules = rulesService.getAllRules();
			return successResponse(rules);
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing getAllRules"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.error(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}
	
	@RequestMapping(value="/getAllTopLevelRules", method=RequestMethod.GET)
	@ResponseBody
	public RestServiceResponse getAllTopLevelRules() {
		try {
			List<Rule> rules = rulesService.getAllTopLevelRules();
			return successResponse(rules);
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing getAllTopLevelRules"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.error(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}
	
	@RequestMapping(value="/getChildRulesForRule", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse getChildRulesForRule(@RequestBody RestServiceRequest<String> request) {
		try {
			Integer parentRuleId = null;
			try {
				parentRuleId = Integer.parseInt(request.getData());
			} catch(NumberFormatException e) {
				//do nothing. Let ruleId be null
			}
			if(parentRuleId == null) throw new PersuasionAPIException("1001", "Failed to retrieve child rules"
					+ ". Invalid parent rule ID");
			List<Rule> rules = rulesService.getAllChildRules(parentRuleId);
			return successResponse(rules);
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing getChildRulesForRule"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.error(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}
	
	@RequestMapping(value="/getRuleById", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse getRuleById(@RequestBody RestServiceRequest<String> request) {
		try {
			Integer ruleId = null;
			try {
				ruleId = Integer.parseInt(request.getData());
			} catch(NumberFormatException e) {
				//do nothing. Let ruleId be null
			}
			if(ruleId == null) throw new PersuasionAPIException("1001", "Invalid rule ID");
			Rule rule = rulesService.getRuleById(ruleId);
			return successResponse(rule);
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing getRuleById"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.error(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}
	
	@RequestMapping(value="/getAllRuleComparators", method=RequestMethod.GET)
	@ResponseBody
	public RestServiceResponse getAllRuleComparators() {
		try {
			List<String> ruleComparators = rulesService.getAllRuleComparators();
			return successResponse(ruleComparators);
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing getAllRuleComparators"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.error(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}
	
	@RequestMapping(value="/editRule", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse editRule(@RequestBody RestServiceRequest<Rule> request) {
		try {
			Rule ruleToBeEdited = request.getData();
			if(ruleToBeEdited == null) throw new PersuasionAPIException("1001", "Invalid request");
			
			Integer ruleId = rulesService.createOrUpdateRule(ruleToBeEdited);
			Rule rule = rulesService.getRuleById(ruleId);
			return successResponse(rule);
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing editRule"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.error(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse deleteRule(@RequestBody RestServiceRequest<String> request) {
		try {
			Integer ruleId = null;
			try {
				ruleId = Integer.parseInt(request.getData());
			} catch(NumberFormatException e) {
				//do nothing. Let ruleId be null
			}
			if(ruleId == null) throw new PersuasionAPIException("1001", "Invalid rule ID");
			rulesService.deleteRule(ruleId);
			return successResponse();
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing deleteRule"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.error(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}
	
	@RequestMapping(value="/updateParent", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse updateParent(@RequestBody RestServiceRequest<Rule> request) {
		try {
			Integer ruleId = null;
			Integer parentRuleId = null;
			try {
				ruleId = Integer.parseInt(request.getData().getRuleId());
				parentRuleId = Integer.parseInt(request.getData().getParentRuleId());
			} catch(NumberFormatException e) {
				//do nothing. Let ruleId be null
			}
			if(ruleId == null)
				throw new PersuasionAPIException("1001", "Invalid rule ID to update hierarchy");
			if(parentRuleId == null)
				throw new PersuasionAPIException("1001", "Invalid parent rule ID to update hierarchy");
			if(ruleId == parentRuleId)
				throw new PersuasionAPIException("1001", "Rule cannot be made parent of itself");
			rulesService.updateParentRule(ruleId, parentRuleId);
			return successResponse();
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), 
					"Failed to update rule hierarchy. Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing updateParent"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.error(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}
	
}
