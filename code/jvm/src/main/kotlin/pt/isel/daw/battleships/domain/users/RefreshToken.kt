package pt.isel.daw.battleships.domain.users

import pt.isel.daw.battleships.domain.exceptions.InvalidRefreshTokenException
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * The RefreshToken entity.
 *
 * @property id the id of the RefreshToken
 * @property user the user that owns the refresh token
 * @property tokenHash the hashed refresh token
 * @property expirationDate the expiration date of the refresh token
 */
@Entity
@Table(name = "refresh_tokens")
class RefreshToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User

    @Column(name = "token_hash", nullable = false)
    val tokenHash: String

    @Column(name = "expiration_date", nullable = false)
    val expirationDate: Timestamp

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(
        user: User,
        tokenHash: String,
        expirationDate: Timestamp,
    ) {
        if (tokenHash.length != TOKEN_HASH_LENGTH)
            throw InvalidRefreshTokenException("The token hash must have a length of $TOKEN_HASH_LENGTH")

        this.user = user
        this.tokenHash = tokenHash
        this.expirationDate = expirationDate
    }

    companion object {
        const val TOKEN_HASH_LENGTH = 128
    }
}
