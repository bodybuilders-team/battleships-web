package pt.isel.daw.battleships.database.repositories

import org.springframework.data.repository.CrudRepository
import pt.isel.daw.battleships.database.model.User

interface UsersRepository : CrudRepository<User, String> {
    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean
}
