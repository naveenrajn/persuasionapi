package osu.ceti.persuasionapi.data.access;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
	
	/**
	 * Retrieves all badges assignmed for the given userId; Returns an empty list if no assignments
	 * @param userId
	 * @return
	 * @throws DatabaseException
	 */
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
	
	/**
	 * Retrieve the time of last badge assigned for a user; Returns null if no assignments
	 * @param userId
	 * @return
	 * @throws DatabaseException
	 */
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

	/**
	 * Update the badge class with the given new value
	 * @param oldClassName
	 * @param newClassName
	 * @throws DatabaseException
	 */
	public void updateBadgeClass(String oldClassName, String newClassName) 
			throws DatabaseException {
		log.debug("updating class name in UserBadgeMapping");
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("update UserBadgeMapping "
					+ "set id.badgeClass=:newValue where id.badgeClass=:oldValue");
			query.setParameter("oldValue", oldClassName);
			query.setParameter("newValue", newClassName);
			query.executeUpdate();
			log.debug("update badge class successful. Old class: " + oldClassName 
					+ ". New class: " + newClassName);
		} catch (Exception e) {
			log.error("Failed to update badge class in UserBadgeMapping"
					+ ". Old class: " + oldClassName 
					+ ". New class: " + newClassName
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to map user badges to the new class"
					+ ": " + e.getMessage());
		}
	}

	/**
	 * Remove all badge assignments for the given badgeId
	 * @param badgeId
	 * @throws DatabaseException
	 */
	public void removeAllAssignmentsForBadge(Integer badgeId) throws DatabaseException {
		log.debug("removing all assignments in userBadgeMapping for badgeId");
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("delete from UserBadgeMapping "
					+ "where badge.badgeId=:badgeId");
			query.setParameter("badgeId", badgeId);
			query.executeUpdate();
			log.debug("removing badge assignments successful for badge Id: " + badgeId);
		} catch (Exception e) {
			log.error("Failed to remove assignments in UserBadgeMapping. Badge Id: " + badgeId
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to remove existing assignments for badge"
					+ ": " + e.getMessage());
		}
	}
}
