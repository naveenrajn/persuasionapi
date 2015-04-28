package osu.ceti.persuasionapi.services.internal.controllers;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.services.ControllerTemplate;
import osu.ceti.persuasionapi.services.RestServiceResponse;
import osu.ceti.persuasionapi.services.internal.BadgeServicesInternal;
import osu.ceti.persuasionapi.services.internal.wrappers.CreateOrEditBadgeRequest;
import osu.ceti.persuasionapi.services.internal.wrappers.BadgeResponse;
import osu.ceti.persuasionapi.services.internal.wrappers.XEditableUpdateRequest;
import osu.ceti.persuasionapi.services.wrappers.RestServiceRequest;

@Controller
@RequestMapping("/badges")
public class BadgesServiceInternalController extends ControllerTemplate {

	private static final Logger log = Logger.getLogger(BadgesServiceInternalController.class);

	@Autowired BadgeServicesInternal badgeServices;

	@RequestMapping(value="/createOrEdit", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse createOrEditBadge(@RequestBody CreateOrEditBadgeRequest request) {
		try {
			Badge newBadge = new Badge();
			newBadge.setBadgeClass(request.getBadgeClass());
			try {
				newBadge.setBadgeLevel(Integer.parseInt(request.getBadgeLevel()));
			} catch(NumberFormatException e) {
				throw new PersuasionAPIException("1001", "Please enter a valid badge level");
			}
			if(StringHelper.isEmpty(request.getBadgeId())) {
				newBadge.setBadgeId(null);
			} else {
				newBadge.setBadgeId(Integer.parseInt(request.getBadgeId()));
			}
			newBadge.setBadgeName(request.getBadgeName());
			newBadge.setBadgeDesc(request.getBadgeDescription());
			newBadge.setEmailSubject(request.getEmailSubject());
			newBadge.setEmailMsg(request.getEmailBody());
			newBadge.setPublicRecognition(request.getSocialUpdate());

			Badge createdBadge = badgeServices.createOrEditBadge(newBadge, request.isNewBadgeClass());
			return successResponse(prepareResponseContent(createdBadge));
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing createOrEditBadge"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}

	/*@RequestMapping(value="/getAllBadges", method=RequestMethod.GET)
	@ResponseBody
	public RestServiceResponse getAllBadges() {

	}*/

	@RequestMapping(value="/updateBadgeClass", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse updateBadgeClass(@RequestBody XEditableUpdateRequest request) {
		try {
			String oldClassName = request.getPk();
			String newClassName = request.getValue();
			if(oldClassName==null || newClassName==null) throw new PersuasionAPIException("1001", 
					"Invalid class name to update");
			if(StringHelper.isEmpty(oldClassName) || StringHelper.isEmpty(newClassName))
				throw new PersuasionAPIException("1001", "Cannot update empty class names");
			if(oldClassName.equalsIgnoreCase(newClassName)) return successResponse();
			badgeServices.updateBadgeClass(request.getPk(), request.getValue());
			return successResponse();
		} catch (PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing updateBadgeClass"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			return failureResponse("1001", 
					"Unexpected Error occured. Reference error: " + e.getMessage());
		}
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public RestServiceResponse deleteBadge(@RequestBody RestServiceRequest<String> request) {
		System.out.println("Received delete request for: " + request.getData());
		try {
			Integer badgeId = null;
			try {
				badgeId = Integer.parseInt(request.getData());
			} catch(NumberFormatException e) {
				throw new PersuasionAPIException("1001", "Invalid badge reference to delete");
			}
			badgeServices.deleteBadge(badgeId);
			return successResponse();
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing deleteBadge"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}
	
	@RequestMapping(value="/getAllBadges", method=RequestMethod.GET)
	@ResponseBody
	public RestServiceResponse getAllBadges() {
		try {
			Map<String, List<BadgeResponse>> badgeList = badgeServices.getAllBadgesGroupedByBadgeClass2();
			return successResponse(badgeList);
		} catch(PersuasionAPIException e) {
			return failureResponse(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("Caught exception while processing getAllBadges"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			return failureResponse("1001", "Unexpected error occured. Reference error: "
					+ e.getMessage());
		}
	}

	private BadgeResponse prepareResponseContent(Badge badge) {
		BadgeResponse response = new BadgeResponse();

		response.setBadgeClass(badge.getBadgeClass());
		response.setBadgeDesc(badge.getBadgeDesc());
		response.setBadgeId(String.valueOf(badge.getBadgeId()));
		response.setBadgeLevel(String.valueOf(badge.getBadgeLevel()));
		response.setBadgeName(badge.getBadgeName());
		response.setEmailSubject(badge.getEmailSubject());
		response.setEmailMsg(badge.getEmailMsg());
		response.setPublicRecognition(badge.getPublicRecognition());

		return response;
	}
}
