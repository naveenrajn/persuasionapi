package osu.ceti.persuasionapi.core.operations;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.Constants;
import osu.ceti.persuasionapi.core.helpers.JMSMessageSender;
import osu.ceti.persuasionapi.data.access.UserBadgeMappingDAO;
import osu.ceti.persuasionapi.data.access.UserDAO;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.data.model.User;
import osu.ceti.persuasionapi.data.model.UserBadgeMapping;
import osu.ceti.persuasionapi.data.model.UserBadgeMappingId;

@Component
public class UserBadgeOperations {
	
	private static final Logger log = Logger.getLogger(UserBadgeOperations.class);
	
	private final String userEmailQueueName = "osu.ceti.persuasionapi.badgenotifications.email";

	@Autowired UserBadgeMappingDAO userBadgeDAO;
	@Autowired UserDAO userDAO;
	
	@Autowired private JMSMessageSender jmsMessageSender;
	@Autowired private JmsTemplate jmsTemplate;
	
	@Autowired UserSocialFeedOperations userSocialFeedOperations;
	
	/**
	 * Retrieve all current badges for user with the given userId
	 * @param userId
	 * @return list of user badges
	 * @throws DatabaseException 
	 */
	public List<UserBadgeMapping> getAllBadgesForUser(String userId) throws DatabaseException {
		return userBadgeDAO.getAllBadgeMappingsForUser(userId);
	}
	
	/**
	 * Retrieve the current badge level for a given user for a given badge class
	 * @param userId
	 * @param badgeClass
	 * @return UserBadgeMapping handle, if found a badge assignment
	 * @throws DatabaseException 
	 */
	public UserBadgeMapping getSingleClassBadgeForUser(String userId, String badgeClass) 
			throws PersuasionAPIException {
		
		UserBadgeMappingId userBadgeMappingId = new UserBadgeMappingId();
		User user = userDAO.findById(userId);
		if(user == null) {
			return null;
		}
		userBadgeMappingId.setUser(user);
		userBadgeMappingId.setBadgeClass(badgeClass);
		
		return userBadgeDAO.findById(userBadgeMappingId);
	}
	
	/**
	 * Assigns the specified badge to the user with the given userId
	 * @param userId
	 * @param badge
	 * @throws PersuasionAPIException 
	 */
	public void assignBadgeForUser(String userId, 
			Badge badge) throws PersuasionAPIException {
		UserBadgeMappingId searchCriteriaId = new UserBadgeMappingId();
		searchCriteriaId.setUser(new User(userId));
		searchCriteriaId.setBadgeClass(badge.getBadgeClass());
		
		boolean newBadgeAssignment = false;
		
		UserBadgeMapping userBadgeMapping = new UserBadgeMapping();
		try {
			userBadgeMapping = userBadgeDAO.findById(searchCriteriaId);
			if(userBadgeMapping == null) {
				userBadgeMapping = new UserBadgeMapping();
				userBadgeMapping.setId(searchCriteriaId);
				newBadgeAssignment = true;
			}
		} catch(Exception e) {
			userBadgeMapping.setId(searchCriteriaId);
		}
		userBadgeMapping.setBadge(badge);
		userBadgeMapping.setProcessedTime(new Date());
		
		userBadgeDAO.merge(userBadgeMapping);
		
		if(newBadgeAssignment) {
			sendEmailNotification(userId, badge);
			postSocialFeedNotification(userId, badge, badge.getPublicRecognition());
		}
	}
	
	/**
	 * Posts notifications to the email queue
	 * @param userId
	 * @param badge
	 */
	private void sendEmailNotification(String userId, Badge badge) {
		if(StringHelper.isEmpty(userId) ||
				StringHelper.isEmpty(badge.getEmailSubject()) || 
				StringHelper.isEmpty(badge.getEmailMsg())) {
			return;
		}
		
		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put(Constants.USER_ID, userId);
		messageMap.put(Constants.EMAIL_SUBJECT, badge.getEmailSubject());
		messageMap.put(Constants.EMAIL_BODY, badge.getEmailMsg());
		
		jmsMessageSender.sendJMSMessage(jmsTemplate, userEmailQueueName, messageMap);
	}
	
	/**
	 * Logs social feed notifications
	 * @param userId
	 * @param notificationText
	 * @throws PersuasionAPIException
	 */
	private void postSocialFeedNotification(String userId, Badge badge,
			String notificationText) 
		throws PersuasionAPIException {
		if(StringHelper.isEmpty(userId) || badge == null ||
				StringHelper.isEmpty(notificationText)) {
			return;
		}
		
		userSocialFeedOperations.logSocialNotification(userId, null,
				badge.getBadgeId(), notificationText);
	}
}
