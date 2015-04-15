package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import osu.ceti.persuasionapi.data.model.RuleQueueMapping;

/**
 * DAO for domain model RuleQueueMapping
 * @see osu.ceti.persuasionapi.data.model.RuleQueueMapping
 */
public class RuleQueueMappingDAO {

	private static final Logger log = Logger.getLogger(RuleQueueMappingDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(RuleQueueMapping transientInstance) {
		log.debug("persisting RuleQueueMapping instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RuleQueueMapping instance) {
		log.debug("attaching dirty RuleQueueMapping instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RuleQueueMapping instance) {
		log.debug("attaching clean RuleQueueMapping instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RuleQueueMapping persistentInstance) {
		log.debug("deleting RuleQueueMapping instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RuleQueueMapping merge(RuleQueueMapping detachedInstance) {
		log.debug("merging RuleQueueMapping instance");
		try {
			RuleQueueMapping result = (RuleQueueMapping) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public RuleQueueMapping findById(
			osu.ceti.persuasionapi.data.model.RuleQueueMappingId id) {
		log.debug("getting RuleQueueMapping instance with id: " + id);
		try {
			RuleQueueMapping instance = (RuleQueueMapping) sessionFactory
					.getCurrentSession()
					.get("osu.ceti.persuasionapi.data.model.RuleQueueMapping",
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

	public List findByExample(RuleQueueMapping instance) {
		log.debug("finding RuleQueueMapping instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"osu.ceti.persuasionapi.data.model.RuleQueueMapping")
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
