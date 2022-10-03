package pt.isel.daw.battleships.database.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * The User entity.
 *
 * @property username The username of the user.
 * @property hashedPassword The hashed password of the user.
 * @property points The points of the user.
 */
@Entity
@Table(name = "users")
class User(
    @Column(name = "username")
    val username: String,

    @Column(name = "hashed_password", nullable = false)
    val hashedPassword: String,

    @Column(name = "points", nullable = false)
    val points: Int = 0
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null
}
