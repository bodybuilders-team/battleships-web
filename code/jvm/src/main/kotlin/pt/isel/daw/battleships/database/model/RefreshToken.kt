package pt.isel.daw.battleships.database.model

import java.time.Instant
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
class RefreshToken(
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "token_hash", nullable = false)
    val tokenHash: String,

    @Column(name = "expiration_date", nullable = false)
    val expirationDate: Instant
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null
}
