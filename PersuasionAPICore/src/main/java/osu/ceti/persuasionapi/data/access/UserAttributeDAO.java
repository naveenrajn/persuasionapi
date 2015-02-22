package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.UserAttribute;

/**
 * DAO for domain model UserAttribute
 * @see osu.ceti.persuasionapi.data.model.UserAttribute
 */
public class UserAttributeDAO {

	private static final Log log = LogFactory.getLog(UserAttributeDAO.class);

private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(UserAttribute transientInstance) {
		log.debug("persisting UserAttribute instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(UserAttribute instance) {
		log.debug("attaching dirty UserAttribute instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserAttribute instance) {
		log.debug("attaching clean UserAttribute instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UserAttribute persistentInstance) {
		log.debug("deleting UserAttribute instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserAttribute merge(UserAttribute detachedInstance) throws DatabaseException {
		log.debug("merging UserAttribute instance");
		try {
			UserAttribute result = (UserAttribute) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception e) {
			log.error("merge UserAttribute to database failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to persist/merge UserAttribute to database", e);
		}
	}

	public UserAttribute findById(java.lang.String id) throws DatabaseException {
		log.debug("getting UserAttribute instance with id: " + id);
		try {
			UserAttribute instance = (UserAttribute) sessionFactory
					.getCurrentSession().get(
							"osu.ceti.persuasionapi.data.model.UserAttribute",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception e) {
			log.error("find UserAttribute by attribute name failed. Attribute Name: " + id
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to lookup UserAttribute from database"
					+ " with name: " + id, e);
		}
	}

	public List findByExample(UserAttribute instance) {
		log.debug("finding UserAttribute instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"osu.ceti.persuasionapi.data.model.UserAttribute")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

}
