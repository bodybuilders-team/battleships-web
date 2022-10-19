package pt.isel.daw.battleships.database.repositories

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.battleships.database.model.User

/**
 * Repository for the [User] entity.
 */
interface UsersRepository : JpaRepository<User, Int> {

    fun existsByEmail(email: String): Boolean

    /**
     * Finds a user by its username.
     *
     * @param username the username of the user to find
     * @return the user with the given username, or null if no such user exists
     */
    fun findByUsername(username: String): User?

    /**
     * Checks if a user with the given username exists.
     *
     * @param username the username of the user to check
     * @return true if a user with the given username exists, false otherwise
     */
    fun existsByUsername(username: String): Boolean
}
