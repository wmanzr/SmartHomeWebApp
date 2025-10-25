package RUT.smart_home.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public abstract class BaseRepository<T, ID> {

    private Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected void create(T entity) {
        entityManager.persist(entity);
    }

    protected T update(T entity) {
        return entityManager.merge(entity);
    }

    public void deleteById(ID id) {
        T entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    protected T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    protected List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityName() + " e", entityClass)
                .getResultList();
    }

    private String entityName() {
        Entity annotation = entityClass.getAnnotation(Entity.class);
        if (annotation != null && annotation.name() != null && !annotation.name().isBlank()) {
            return annotation.name();
        }
        return entityClass.getSimpleName();
    }
}