/**
 * 
 */
package org.jenkins.plugins.dbaudit.internal.data;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jenkins.plugins.dbaudit.data.BuildDetailsRepository;
import org.jenkins.plugins.dbaudit.model.BuildDetails;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Hibernate-based implementation of the {@link BuildDetailsRepository} interface.
 * 
 * @author Marco Scata
 *
 */
public class BuildDetailsHibernateRepository implements BuildDetailsRepository {
	private final HibernateTemplate hibernate = new HibernateTemplate();
	
	public BuildDetailsHibernateRepository(final SessionFactory sessionFactory) {
		this.hibernate.setSessionFactory(sessionFactory);
	}
	
	/**
	 * @see org.jenkins.plugins.dbaudit.data.BuildDetailsRepository#saveBuildDetails(org.jenkins.plugins.dbaudit.model.BuildDetails)
	 */
	@Override
	public Object saveBuildDetails(final BuildDetails details) {
		if (null == details) {
			throw new IllegalArgumentException("Invalid build details: cannot be null.");
		}
		return hibernate.save(details);
	}

	/**
	 * @see org.jenkins.plugins.dbaudit.data.BuildDetailsRepository#getBuildDetailsByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BuildDetails> getBuildDetailsByName(final String name) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(BuildDetails.class);
		criteria.add(Restrictions.ilike("name", name, MatchMode.EXACT));
		return hibernate.findByCriteria(criteria);
	}

	/**
	 * @see org.jenkins.plugins.dbaudit.data.BuildDetailsRepository#getBuildDetailsByFullName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BuildDetails> getBuildDetailsByFullName(final String fullName) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(BuildDetails.class);
		criteria.add(Restrictions.ilike("fullName", fullName, MatchMode.EXACT));
		return hibernate.findByCriteria(criteria);
	}

	/**
	 * @see org.jenkins.plugins.dbaudit.data.BuildDetailsRepository#getBuildDetailsByDateRange(java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BuildDetails> getBuildDetailsByDateRange(final Date start, final Date end) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(BuildDetails.class);
		// we need this funny-looking complex criteria because the
		// semantics of the 'between' criteria can vary across
		// db providers and we want a predictable inclusive behaviour.
		criteria.add(Restrictions.or(
				Restrictions.and(
					Restrictions.ge("startDate", start),
					Restrictions.le("startDate", end)),
				Restrictions.and(
						Restrictions.ge("endDate", start),
						Restrictions.le("endDate", end))
		));
		return hibernate.findByCriteria(criteria);
	}

	/**
	 * @see org.jenkins.plugins.dbaudit.data.BuildDetailsRepository#getBuildDetailsByDuration(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BuildDetails> getBuildDetailsByDurationRange(final long min, final long max) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(BuildDetails.class);
		// we need this funny-looking complex criteria because the
		// semantics of the 'between' criteria can vary across
		// db providers and we want a predictable inclusive behaviour.
		criteria.add(
				Restrictions.and(
					Restrictions.ge("duration", min),
					Restrictions.le("duration", max)
		));
		return hibernate.findByCriteria(criteria);
	}

	/**
	 * @see org.jenkins.plugins.dbaudit.data.BuildDetailsRepository#getBuildDetailsByUserId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BuildDetails> getBuildDetailsByUserId(final String userId) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(BuildDetails.class);
		criteria.add(Restrictions.ilike("userId", userId, MatchMode.EXACT));
		return hibernate.findByCriteria(criteria);
	}

	/**
	 * @see org.jenkins.plugins.dbaudit.data.BuildDetailsRepository#getBuildDetailsByUserName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BuildDetails> getBuildDetailsByUserName(final String userName) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(BuildDetails.class);
		criteria.add(Restrictions.ilike("userName", userName, MatchMode.EXACT));
		return hibernate.findByCriteria(criteria);
	}

	/**
	 * @see org.jenkins.plugins.dbaudit.data.BuildDetailsRepository#updateBuildDetails(org.jenkins.plugins.dbaudit.model.BuildDetails)
	 */
	@Override
	public void updateBuildDetails(final BuildDetails details) {
		if (null == details) {
			throw new IllegalArgumentException("Invalid build details: cannot be null.");
		}
		hibernate.update(details);
	}

}
