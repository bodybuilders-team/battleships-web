package pt.isel.daw.battleships.database

import java.util.Optional
import java.util.function.Predicate
import javax.persistence.EntityManager
import javax.persistence.LockModeType

/**
 * Represents a repository for a specific entity.
 * A repository is a class that provides CRUD operations for a specific entity collection.
 *
 * @param <T> the type of the entity
 * @param entityManager the entity manager
 * @param genericType the generic type of the entity
 */
abstract class Repository<T>(
    private val entityManager: EntityManager,
    private val genericType: Class<T>
) {
    private val mapper = object : Mapper<T>(entityManager, genericType) {}

    /**
     * Adds the given entity to the repository.
     *
     * @param entity the entity to be added
     */
    fun add(entity: T) {
        mapper.create(entity)
    }

    /**
     * Updates the given entity in the repository.
     *
     * @param entity the entity to be updated
     */
    fun update(entity: T) {
        mapper.update(entity)
    }

    /**
     * Removes the given entity from the repository.
     *
     * @param entity the entity to be removed
     */
    fun remove(entity: T) {
        mapper.delete(entity)
    }

    /**
     * Removes all entities.
     */
    fun removeAll() {
        entityManager
            .createQuery("DELETE FROM " + genericType.simpleName)
            .executeUpdate()
    }

    /**
     * Gets an entity given a predicate.
     *
     * @param predicate the predicate to be used
     * @return an optional discribing the first entity that matches the predicate,
     * or an empty optional if no entity matches
     */
    operator fun get(predicate: Predicate<T>?): Optional<T> {
        return entityManager
            .createQuery("SELECT entity FROM " + genericType.simpleName + " entity", genericType)
            .setLockMode(LockModeType.PESSIMISTIC_READ)
            .resultStream
            .filter(predicate)
            .findFirst()
    }

    /**
     * Gets all entities.
     *
     * @return list with all entities
     */
    val all: List<T>
        get() = entityManager
            .createQuery("SELECT entity FROM " + genericType.simpleName + " entity", genericType)
            .setLockMode(LockModeType.PESSIMISTIC_READ)
            .resultList

    /**
     * Gets an entity by its id.
     *
     * @param id the id of the entity to be read
     * @return the entity with the given id
     */
    fun getById(id: Int): T? {
        return mapper.read(id)
    }
}
