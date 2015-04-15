package osu.ceti.persuasionapi.core.operations;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.data.access.UserSocialNotificationDAO;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.data.model.Rule;
import osu.ceti.persuasionapi.data.model.User;
import osu.ceti.persuasionapi.data.model.UserSocialNotification;

@Component
public class UserSocialFeedOperations {

	private static final Logger log = Logger.getLogger(UserSocialFeedOperations.class);
	
	@Autowired UserSocialNotificationDAO userSocialNotificationDAO;
	@Autowired UserOperations userOperations;
	@Autowired RuleOperations ruleOperations;
	@Autowired BadgeOperations badgeOperations;
	
	public List<UserSocialNotification> getNotifications(String userId, Date startTime) 
			throws DatabaseException {
		return userSocialNotificationDAO.getNotificationsAfterTime(userId, startTime);
	}
	
	public void logSocialNotification(User user, Rule rule, Badge badge,
			String notificationText) throws PersuasionAPIException {
		
		if(user == null || (rule == null && badge == null)) {
			log.error("Insufficient information to post user social notification"
					+ ". User: " + user
					+ ". Rule: " + rule
					+ ". Badge: " + badge);
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION, 
					"Insufficient information to post user social notification");
		}
		
		UserSocialNotification notification = findExistingNotification(
				user, rule, badge);
		
		if(notification == null) {
			notification = new UserSocialNotification();
			notification.setUser(user);
			notification.setRule(rule);
			notification.setBadge(badge);
		}
		
		notification.setNotificationText(notificationText);
		//TODO: Modify this to use DB timestamp
		notification.setPostedTime(new Date());
		
		userSocialNotificationDAO.merge(notification);
	}
	
	public void logSocialNotification(String userId, Integer ruleId, Integer badgeId,
			String notificationText) throws PersuasionAPIException {
		User user = userOperations.lookupUser(userId);
		Rule rule = (ruleId == null) ? null : ruleOperations.lookupRule(ruleId);
		Badge badge = (badgeId == null) ? null : badgeOperations.lookupBadge(badgeId);
		logSocialNotification(user, rule, badge, notificationText);
	}
	
	private UserSocialNotification findExistingNotification(User user,
			Rule rule, Badge badge) throws PersuasionAPIException {
		if(rule != null && badge != null) {
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION, 
					"Error finding existing user social notification to update");
		}
		if(rule != null) {
			return userSocialNotificationDAO.findUserNotificationForRule(
					user.getUserId(), rule.getRuleId());
		}
		if(badge != null) {
			return userSocialNotificationDAO.findUserNotificationForBadge(
					user.getUserId(), badge.getBadgeId());
		}
		return null;
	}

}
