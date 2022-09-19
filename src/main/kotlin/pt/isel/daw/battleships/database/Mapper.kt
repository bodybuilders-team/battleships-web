package pt.isel.daw.battleships.database

import javax.persistence.EntityManager
import javax.persistence.LockModeType

/**
 * Represents a mapper for a specific entity.
 * A mapper is a class that provides CRUD operations for a specific entity.
 *
 * @param T the type of the entity
 * @param entityManager the entity manager
 * @param genericType the generic type of the entity
 */
abstract class Mapper<T>(
    private val entityManager: EntityManager,
    private val genericType: Class<T> // TODO: Check this out
) {

    /**
     * Creates a new entity.
     *
     * @param entity the entity to be created
     */
    fun create(entity: T) {
        entityManager.persist(entity)
    }

    /**
     * Reads an entity by its id.
     *
     * @param id the id of the entity to be read
     * @return the entity with the given id
     */
    fun read(id: Int): T? {
        return entityManager.find(genericType, id, LockModeType.PESSIMISTIC_READ)
    }

    /**
     * Updates an entity.
     *
     * @param entity the entity to be updated
     * @return the updated entity
     */
    fun update(entity: T): T {
        entityManager.flush()
        entityManager.lock(entity, LockModeType.PESSIMISTIC_WRITE)
        return entityManager.merge(entity)
    }

    /**
     * Deletes an entity.
     *
     * @param entity the entity to be deleted
     */
    fun delete(entity: T) {
        entityManager.remove(entity)
    }
}
