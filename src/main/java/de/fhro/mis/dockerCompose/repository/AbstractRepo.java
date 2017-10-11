package de.fhro.mis.dockerCompose.repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Kurfer
 * Created on 8/26/17.
 */
public abstract class AbstractRepo<T> implements Repository<T> {
	private Class<T> entityClass;

	public AbstractRepo(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	public void create(T entity) {
		getEntityManager().persist(entity);
	}

	public void edit(T entity) {
		getEntityManager().merge(entity);
	}

	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	public T findWithGraph(Object id, String graphName) {
		EntityGraph graph = getEntityManager().getEntityGraph(graphName);
		Map<String, Object> hints = new HashMap<>();
		hints.put("javax.persistence.fetchgraph", graph);
		return getEntityManager().find(entityClass, id, hints);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(int take, int skip) {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		TypedQuery<T> typedQuery = getEntityManager().createQuery(cq.select(cq.from(entityClass)));
		typedQuery.setFirstResult(skip);
		typedQuery.setMaxResults(take);
		return typedQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllWithGraph(String graphName) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		EntityGraph<T> entityGraph = (EntityGraph<T>) getEntityManager().getEntityGraph(graphName);
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return getEntityManager()
				.createQuery(criteriaQuery)
				.setHint("javax.persistence.fetchgraph", entityGraph)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllWithGraph(String graphName, int take, int skip) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		EntityGraph<T> entityGraph = (EntityGraph<T>) getEntityManager().getEntityGraph(graphName);
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return getEntityManager()
				.createQuery(criteriaQuery)
				.setFirstResult(skip)
				.setMaxResults(take)
				.setHint("javax.persistence.fetchgraph", entityGraph)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public long count() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
		Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return (Long) q.getSingleResult();
	}

	public List<T> findByQuery(String queryName) {
		return findByQuery(queryName, new HashMap<>());
	}

	public List<T> findByQuery(String queryName, Map<String, Object> parameters) {
		try {
			TypedQuery<T> namedQuery = getEntityManager().createNamedQuery(queryName, entityClass);

			for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
				namedQuery.setParameter(parameter.getKey(), parameter.getValue());
			}

			return namedQuery.getResultList();
		} catch (Exception ex) {
			return null;
		}
	}

}
