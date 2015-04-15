package osu.ceti.persuasionapi.data.access;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.User;
import osu.ceti.persuasionapi.data.model.UserBadgeMapping;
import osu.ceti.persuasionapi.data.model.UserBadgeMappingId;

/**
 * DAO for domain model UserBadgeMapping
 * @see osu.ceti.persuasionapi.data.model.UserBadgeMapping
 */
public class UserBadgeMappingDAO {

	private static final Logger log = Logger.getLogger(UserBadgeMappingDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserBadgeMapping merge(UserBadgeMapping detachedInstance) 
			throws DatabaseException {
		log.debug("merging UserBadgeMappings instance");
		try {
			UserBadgeMapping result = (UserBadgeMapping) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception e) {
			log.error("merge UserBadgeMappings to database failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to persist/merge UserBadgeMappings"
					+ " to database", e);
		}
	}

	public UserBadgeMapping findById(UserBadgeMappingId id) throws DatabaseException {
		log.debug("getting UserBadgeMappings instance with id: " + id);
		try {
			UserBadgeMapping instance = (UserBadgeMapping) sessionFactory
					.getCurrentSession().get(UserBadgeMapping.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception e) {
			log.error("find UserBadgeMappings by Id failed"
					+ ". User ID: " + id.getUser().getUserId()
					+ ". Badge Class: " + id.getBadgeClass()
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve UserBadgeMappings from database", e);
		}
	}
	
	public List getAllBadgeMappingsForUser(String userId) throws DatabaseException {
		log.debug("finding UserBadgeMappings for user: " + userId);
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(UserBadgeMapping.class)
					.add(Restrictions.eq("id.user.userId", userId)).list();
			log.debug("find by userId successful, result size: "
					+ results.size());
			return results;
		} catch (Exception e) {
			log.error("find UserBadgeMappings failed for user: " + userId
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve UserBadgeMappings from database"
					+ " for user: " + userId, e);
		}
	}
	
	public Date getLastProcessedTimeForUser(String userId) throws DatabaseException {
		log.debug("finding last processed time for user with id: " + userId);
		
		UserBadgeMappingId userBadgeMappingsId = new UserBadgeMappingId();
		userBadgeMappingsId.setUser(new User(userId));
		UserBadgeMapping userBadgeMapping = new UserBadgeMapping();
		userBadgeMapping.setId(userBadgeMappingsId);
		
		try {
			Criteria selectCriteria = sessionFactory.getCurrentSession()
					.createCriteria(UserBadgeMapping.class);
			selectCriteria.add(Example.create(userBadgeMapping));
			selectCriteria.setProjection(Projections.max("processedTime"));
			
			Date lastProcessedDate = (Date) selectCriteria.uniqueResult();
			log.debug("get last processed time for user successful, resultant date: "
					+ lastProcessedDate);
			return lastProcessedDate;
		} catch (Exception e) {
			log.error("get last badge mappings processed time failed for user: " + userId
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve last processing time for"
					+ " UserBadgeMappings from database for user: " + userId, e);
		}
	}
}
