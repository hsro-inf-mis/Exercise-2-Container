package de.fhro.mis.dockerCompose.repository;


import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Peter Kurfer
 * Created on 8/26/17.
 */
@Singleton
public class RepoProducer {

	private final Logger logger;
	@Produces
	@PersistenceContext(name = "week02-docker-compose")
	private EntityManager entityManager;

	public RepoProducer() {
		this.logger = Logger.getLogger(RepoProducer.class.getName());
	}

	@Produces
	@Default
	@SuppressWarnings("unchecked")
	public <T> Repository<T> createRepo(EntityManager entityManager, InjectionPoint injectionPoint) {
		try {
		    /* note: Abandon all hope, ye who enter here
	         * this is a hack to get repositories produced without inventory all implementations */
			Class<T> type = (Class<T>) ((ParameterizedType) injectionPoint.getType()).getActualTypeArguments()[0];
			return new DefaultRepository<>(type, entityManager);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e, () -> "Failed to create repository instance");
		}
		return null;
	}

	private static class DefaultRepository<T> extends AbstractRepo<T> {

		private EntityManager entityManager;

		DefaultRepository(Class<T> entityClass, EntityManager entityManager) {
			super(entityClass);
			this.entityManager = entityManager;
		}

		@Override
		protected EntityManager getEntityManager() {
			return entityManager;
		}
	}
}
