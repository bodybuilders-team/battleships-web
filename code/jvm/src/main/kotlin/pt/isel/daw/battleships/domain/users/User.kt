package pt.isel.daw.battleships.domain.users

import pt.isel.daw.battleships.domain.exceptions.InvalidUserException
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
    private var id: Int? = null

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
        if (username.length !in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH)
            throw InvalidUserException(
                "Username must be between $MIN_USERNAME_LENGTH and $MAX_USERNAME_LENGTH characters long."
            )

        if (!email.matches(EMAIL_REGEX.toRegex()))
            throw InvalidUserException("Email must be a valid email address.")

        if (passwordHash.length != PASSWORD_HASH_LENGTH)
            throw InvalidUserException("Password hash must have a length of $PASSWORD_HASH_LENGTH.")

        if (points < 0)
            throw InvalidUserException("Points must be a positive integer.")

        if (numberOfGamesPlayed < 0)
            throw InvalidUserException("Number of games played must be a positive integer.")

        this.username = username
        this.email = email
        this.passwordHash = passwordHash
        this.points = points
        this.numberOfGamesPlayed = numberOfGamesPlayed
    }

    companion object {
        private const val MIN_USERNAME_LENGTH = 3
        private const val MAX_USERNAME_LENGTH = 40

        private const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)\$"

        const val PASSWORD_HASH_LENGTH = 128
    }
}
