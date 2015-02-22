package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import osu.ceti.persuasionapi.data.model.RuleComparison;

/**
 * DAO for domain model RuleComparison
 * @see osu.ceti.persuasionapi.data.model.RuleComparison
 */
public class RuleComparisonDAO {

	private static final Log log = LogFactory.getLog(RuleComparisonDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(RuleComparison transientInstance) {
		log.debug("persisting RuleComparison instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RuleComparison instance) {
		log.debug("attaching dirty RuleComparison instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RuleComparison instance) {
		log.debug("attaching clean RuleComparison instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RuleComparison persistentInstance) {
		log.debug("deleting RuleComparison instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RuleComparison merge(RuleComparison detachedInstance) {
		log.debug("merging RuleComparison instance");
		try {
			RuleComparison result = (RuleComparison) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public RuleComparison findById(java.lang.Integer id) {
		log.debug("getting RuleComparison instance with id: " + id);
		try {
			RuleComparison instance = (RuleComparison) sessionFactory
					.getCurrentSession().get(
							"osu.ceti.persuasionapi.data.model.RuleComparison",
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

	public List findByExample(RuleComparison instance) {
		log.debug("finding RuleComparison instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"osu.ceti.persuasionapi.data.model.RuleComparison")
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
