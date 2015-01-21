package osu.ceti.persuasionapi.async.listeners;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.async.BadgeRuleProcessor;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.Constants;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.access.UserBadgeMappingsDAO;

/**
 * Listener for user activity log update messages
 * @see application-context.xml
 *
 */
@Component
public class BadgeRuleProcessingListener implements MessageListener {
	
	private static final Log log = LogFactory.getLog(BadgeRuleProcessingListener.class);
	
	@Autowired UserBadgeMappingsDAO userBadgeMappingsDAO;
	@Autowired BadgeRuleProcessor badgeRuleProcessor;
	
	/**
	 * Listens to user activity update messages and triggers badge processing
	 */
	@Transactional
	public void onMessage(Message message) {
		System.out.println("Message received");
		
		if(message instanceof MapMessage) {
			final MapMessage mapMessage = (MapMessage) message;
			try {
				String userId = mapMessage.getString(Constants.USER_ID);
				System.out.println(mapMessage.getString(Constants.USER_ID));
				
				String dateString = mapMessage.getString(Constants.TIMESTAMP);
				Date messageTimeStamp = StringHelper.toDateFromCharString(dateString);
				
				Date userBadgeLastProcessedTime = userBadgeMappingsDAO
						.getLastProcessedTimeForUser(userId);
				
				if(userBadgeLastProcessedTime == null
						|| messageTimeStamp.after(userBadgeLastProcessedTime)) {
					log.debug("New update. Processing rules");
					badgeRuleProcessor.processRulesForUser(userId);
				} else {
					log.debug("Already processed. Skipping update");
				}
			} catch (JMSException e) {
				log.error("Failed to decipher activity update message"
						+ ". Stop processing current message");
			} catch (PersuasionAPIException e) {
				log.error("Failed to process badge rules. Stop processing current message");
			} catch (Exception e) {
				log.error("Caught exception while processing current message"
						+ ". Exception type: " + e.getClass().getName()
						+ ". Exception message: " + e.getMessage());
				log.debug(StringHelper.stackTraceToString(e));
				log.info("Stop processing current message");
			}
		} else {
			log.error("Failed to decipher activity update message"
					+ ". Stop processing current message");
		}
	}
}
