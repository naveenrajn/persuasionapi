package osu.ceti.persuasionapi.data.access;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.UserSocialNotification;

/**
 * DAO for domain model UserSocialNotification
 * @see osu.ceti.persuasionapi.data.model.UserSocialNotification
 */
public class UserSocialNotificationDAO {

	private static final Logger log = Logger.getLogger(UserSocialNotificationDAO.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(UserSocialNotification transientInstance) {
		log.debug("persisting UserSocialNotification instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(UserSocialNotification instance) {
		log.debug("attaching dirty UserSocialNotification instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserSocialNotification instance) {
		log.debug("attaching clean UserSocialNotification instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UserSocialNotification persistentInstance) {
		log.debug("deleting UserSocialNotification instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserSocialNotification merge(UserSocialNotification detachedInstance) 
			throws DatabaseException {
		log.debug("merging UserSocialNotification instance");
		try {
			UserSocialNotification result = (UserSocialNotification) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception e) {
			log.error("Merging/Persisting UserSocialNotification to database failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve UserSocialNotificaitons "
					+ "from database", e);
		}
	}

	public UserSocialNotification findById(java.lang.Integer id) {
		log.debug("getting UserSocialNotification instance with id: " + id);
		try {
			UserSocialNotification instance = (UserSocialNotification) sessionFactory
					.getCurrentSession()
					.get("osu.ceti.persuasionapi.data.model.UserSocialNotification",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserSocialNotification instance) {
		log.debug("finding UserSocialNotification instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"osu.ceti.persuasionapi.data.model.UserSocialNotification")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public List getNotificationsAfterTime(String userId, Date startTime) throws DatabaseException {
		log.debug("finding UserSocialNotification for user after time");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria(UserSocialNotification.class)
					.add(Restrictions.eq("user.userId", userId))
					.add(Restrictions.gt("postedTime", startTime)).list();
			log.debug("find by userId and timestamp, result size: "
					+ results.size());
			return results;
		} catch (Exception e) {
			log.error("find UserSocialNotification by userId and timestamp lower bound failed"
					+ ". User ID: " + userId
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve UserSocialNotificaitons from database", e);
		}
		
	}

	public UserSocialNotification findUserNotificationForRule(String userId,
			Integer ruleId) throws DatabaseException {
		// TODO Auto-generated method stub
		log.debug("finding UserSocialNotification for user for rule");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria(UserSocialNotification.class)
					.add(Restrictions.eq("user.userId", userId))
					.add(Restrictions.eq("rule.ruleId", ruleId)).list();
			log.debug("find by userId and ruleId, result size: "
					+ results.size());
			if(results.size()>0) {
				return (UserSocialNotification) results.get(0);
			} else if (results.size() == 0) {
				return null;
			} else {
				log.error("More than one social notification found");
				throw new Exception("More than one matching social notification found");
			}
		} catch (Exception e) {
			log.error("find UserSocialNotification by userId and ruleId failed"
					+ ". User ID: " + userId
					+ ". Rule ID: " + ruleId
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve "
					+ "UserSocialNotificaitons from database", e);
		}
	}
	
	public UserSocialNotification findUserNotificationForBadge(String userId,
			Integer badgeId) throws DatabaseException {
		// TODO Auto-generated method stub
		log.debug("finding UserSocialNotification for user for badge");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria(UserSocialNotification.class)
					.add(Restrictions.eq("user.userId", userId))
					.add(Restrictions.eq("badge.badgeId", badgeId)).list();
			log.debug("find by userId and ruleId, result size: "
					+ results.size());
			if(results.size()>0) {
				return (UserSocialNotification) results.get(0);
			} else if (results.size() == 0) {
				return null;
			} else {
				log.error("More than one social notification found");
				throw new Exception("More than one matching social notification found");
			}
		} catch (Exception e) {
			log.error("find UserSocialNotification by userId and badgeId failed"
					+ ". User ID: " + userId
					+ ". Badge ID: " + badgeId
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve "
					+ "UserSocialNotificaitons from database", e);
		}
	}

	public void removeAllNotificationsForBadge(Integer badgeId) throws DatabaseException {
		log.debug("removing all items in userSocialNotification for badgeId");
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("update UserSocialNotification "
					+ "set badge.badgeId=null where badge.badgeId=:badgeId");
			query.setParameter("badgeId", badgeId);
			query.executeUpdate();
			log.debug("removing social notifications successful for badge Id: " + badgeId);
		} catch (Exception e) {
			log.error("Failed to remove social notifications. Badge Id: " + badgeId
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to remove social notifications generated by badge"
					+ ": " + e.getMessage());
		}
	}

	public void removeAllNotificationsForRule(Integer ruleId) throws DatabaseException {
		log.debug("removing all items in userSocialNotification for ruleId");
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("update UserSocialNotification "
					+ "set rule.ruleId=null where rule.ruleId=:ruleId");
			query.setParameter("ruleId", ruleId);
			query.executeUpdate();
			log.debug("removing social notifications successful for rule Id: " + ruleId);
		} catch (Exception e) {
			log.error("Failed to remove social notifications. Rule Id: " + ruleId
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to remove social notifications generated by rule"
					+ ": " + e.getMessage());
		}
	}
}
