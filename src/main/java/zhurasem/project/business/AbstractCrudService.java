package zhurasem.project.business;

import org.springframework.data.repository.CrudRepository;
import zhurasem.project.api.exceptions.EntityStateException;
import zhurasem.project.domain.DomainEntity;

import java.util.Optional;

/**
 * Common superclass for business logic of all entities supporting operations Create, Read, Update, Delete.
 *
 * @param <K> Type of (primary) key.
 * @param <E> Type of entity
 */
public abstract class AbstractCrudService<E extends DomainEntity<K>, K> {

    /**
     * Reference to data (persistence) layer.
     */
    protected final CrudRepository<E, K> repository;

    protected AbstractCrudService(CrudRepository<E, K> repository) {
        this.repository = repository;
    }

    /**
     * Attempts to store a new entity. Throws exception if an entity with the same key is already stored.
     *
     * @param entity entity to be stored
     * @throws EntityStateException if an entity with the same key is already stored
     */
    public E create(E entity) throws EntityStateException {
        K id = entity.getId();
        if (id != null && repository.existsById(id))
            throw new EntityStateException(entity);
        return repository.save(entity); //delegate call to data layer
    }

    public Optional<E> readById(K id) {
        return repository.findById(id);
    }

    public Iterable<E> readAll() {
        return repository.findAll();
    }

    /**
     * Attempts to replace an already stored entity.
     *
     * @param entity the new state of the entity to be updated; the instance must contain a key value
     * @throws EntityStateException if the entity cannot be found
     */
    public E update(E entity) throws EntityStateException {
        if (repository.existsById(entity.getId()))
            return repository.save(entity);
        else
            throw new EntityStateException(entity);
    }

    public void deleteById(K id) {
        repository.deleteById(id);
    }

}
