package pt.isel.daw.battleships.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * The User entity.
 *
 * @property id the id of the user
 * @property username the username of the user
 * @property email the email of the user
 * @property passwordHash the hashed password of the user
 * @property points the points of the user
 * @property numberOfGamesPlayed the number of games played by the user
 */
@Entity
@Table(name = "users")
class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "username", nullable = false, unique = true)
    val username: String

    @Column(name = "email", nullable = false, unique = true)
    val email: String

    @Column(name = "password_hash", nullable = false)
    val passwordHash: String

    @Column(name = "points", nullable = false)
    var points: Int

    @Column(name = "number_of_games_played", nullable = false)
    var numberOfGamesPlayed: Int

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(
        username: String,
        email: String,
        passwordHash: String,
        points: Int = 0,
        numberOfGamesPlayed: Int = 0
    ) {
        // TODO: Add Validations
        this.username = username
        this.email = email
        this.passwordHash = passwordHash
        this.points = points
        this.numberOfGamesPlayed = numberOfGamesPlayed
    }
}