package de.fhro.mis.dockerCompose.repository;

import java.util.List;
import java.util.Map;

/**
 * @author Peter Kurfer
 * Created on 8/26/17.
 */
public interface Repository<T> {
	/**
	 * create a new entity in the database
	 *
	 * @param entity entity which should be created
	 */
	void create(T entity);

	/**
	 * update an existing entity
	 *
	 * @param entity entity which should be updated
	 */
	void edit(T entity);

	/**
	 * remove an existing entity
	 *
	 * @param entity entity which sould be removed
	 */
	void remove(T entity);

	/**
	 * find an existing entity by its ID
	 *
	 * @param id id of the entity which should be resolved
	 * @return found entity or null
	 */
	T find(Object id);

	/**
	 * find an existing entity by its ID with a specified entity graph
	 *
	 * @param id        id of the entity which should be resolved
	 * @param graphName name of the entity graph
	 * @return found entity or null
	 */
	T findWithGraph(Object id, String graphName);

	/**
	 * get all entities
	 *
	 * @return List of all entities
	 */
	List<T> findAll();

	/**
	 * get all entities with paging
	 *
	 * @param take number of entities to fetch from database
	 * @param skip number of entities which should be skipped in database
	 * @return list of entities in the specified range
	 */
	List<T> findAll(int take, int skip);

	/**
	 * get all entities with specified entity graph
	 *
	 * @param graphName name of the graph which should be included
	 * @return list of entities
	 */
	List<T> findAllWithGraph(String graphName);

	/**
	 * get all entities with specified entity graph in specified range
	 *
	 * @param graphName name of the graph which should be inclueded
	 * @param take      number of entities to fetch from database
	 * @param skip      number of entities which should be skipped in database
	 * @return list of entities
	 */
	List<T> findAllWithGraph(String graphName, int take, int skip);

	/**
	 * find all entities specified by submitted query
	 *
	 * @param query name of an entity query which resolves to a list of entities
	 * @return list of entities
	 */
	List<T> findByQuery(String query);

	/**
	 * find all entities specified by submitted query with submitted parameters
	 *
	 * @param query      name of the entity query which should be used
	 * @param parameters parameters for the specified query
	 * @return list of entities matched by parameters
	 */
	List<T> findByQuery(String query, Map<String, Object> parameters);

	/**
	 * count of entities of current type
	 *
	 * @return count of entities
	 */
	long count();
}
