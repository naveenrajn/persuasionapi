package osu.ceti.persuasionapi.data.access;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.UserBadgeMappings;
import osu.ceti.persuasionapi.data.model.UserBadgeMappingsId;
import osu.ceti.persuasionapi.data.model.Users;

/**
 * DAO for domain model UserBadgeMappings
 * @see osu.ceti.persuasionapi.data.model.UserBadgeMappings
 */
public class UserBadgeMappingsDAO {

	private static final Log log = LogFactory.getLog(UserBadgeMappingsDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserBadgeMappings merge(UserBadgeMappings detachedInstance) 
			throws DatabaseException {
		log.debug("merging UserBadgeMappings instance");
		try {
			UserBadgeMappings result = (UserBadgeMappings) sessionFactory
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

	public UserBadgeMappings findById(UserBadgeMappingsId id) throws DatabaseException {
		log.debug("getting UserBadgeMappings instance with id: " + id);
		try {
			UserBadgeMappings instance = (UserBadgeMappings) sessionFactory
					.getCurrentSession().get(UserBadgeMappings.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception e) {
			log.error("find UserBadgeMappings by Id failed"
					+ ". User ID: " + id.getUsers().getUserId()
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
					.createCriteria(UserBadgeMappings.class)
					.add(Restrictions.eq("id.users.userId", userId)).list();
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
		
		UserBadgeMappingsId userBadgeMappingsId = new UserBadgeMappingsId();
		userBadgeMappingsId.setUsers(new Users(userId));
		UserBadgeMappings userBadgeMappings = new UserBadgeMappings();
		userBadgeMappings.setId(userBadgeMappingsId);
		
		try {
			Criteria selectCriteria = sessionFactory.getCurrentSession()
					.createCriteria(UserBadgeMappings.class);
			selectCriteria.add(Example.create(userBadgeMappings));
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
