package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.RuleComparison;

/**
 * DAO for domain model RuleComparison
 * @see osu.ceti.persuasionapi.data.model.RuleComparison
 */
public class RuleComparisonDAO {

	private static final Logger log = Logger.getLogger(RuleComparisonDAO.class);

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

	public RuleComparison merge(RuleComparison detachedInstance) throws DatabaseException {
		log.debug("merging RuleComparison instance");
		try {
			RuleComparison result = (RuleComparison) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception e) {
			log.error("Failed to create/update rule comparison" + detachedInstance.toString()
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to create/update rule comparison", e);
		}
	}

	public RuleComparison findById(java.lang.Integer id) throws DatabaseException {
		log.debug("getting RuleComparison instance with id: " + id);
		try {
			RuleComparison instance = (RuleComparison) sessionFactory
					.getCurrentSession().get(RuleComparison.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception e) {
			log.error("Failed to retrieve RuleComparison by Id: " + id
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve all Rule Comparators from database", e);
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

	public void removeAllOtherComparisonsForRule(Integer ruleId, 
			List<Integer> listOfComparisonsForRule) throws DatabaseException {
		log.debug("deleting all invalid rule comparisons");
		try {
			String queryString = "delete from RuleComparison where rule.ruleId=:ruleId";
			if(listOfComparisonsForRule!=null && listOfComparisonsForRule.size()>0)
				queryString += " and ruleCompId not in (:validIds)";
			Query query = sessionFactory.getCurrentSession().createQuery(queryString);
			query.setParameter("ruleId", ruleId);
			if(listOfComparisonsForRule!=null && listOfComparisonsForRule.size()>0)
				query.setParameterList("validIds", listOfComparisonsForRule);
			query.executeUpdate();
			log.debug("delete invalid rule comparisons succesful for rule: " + ruleId 
					+ ". Valid Rule Comparison IDs: " + listOfComparisonsForRule.toArray());
		} catch (Exception e) {
			log.error("Failed to delete invalid rule comparisons for rule: " + ruleId 
					+ ". Valid Rule Comparison IDs: " + listOfComparisonsForRule.toArray()
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to delete invalid rule comparisons for rule: " + ruleId
					+ ". Error: " + e.getMessage());
		}
	}
}
