package pt.isel.daw.battleships.repository.users

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.battleships.domain.users.RevokedAccessToken
import pt.isel.daw.battleships.domain.users.User
import java.sql.Timestamp

/**
 * Repository for the [RevokedAccessToken] entity.
 */
interface RevokedAccessTokensRepository : JpaRepository<RevokedAccessToken, Int> {

    /**
     * Finds a [RevokedAccessToken] given a user and the token hash.
     *
     * @param user the user
     * @param tokenHash the token hash
     *
     * @return the [RevokedAccessToken] if found, null otherwise
     */
    fun findByUserAndTokenHash(user: User, tokenHash: String): RevokedAccessToken?

    /**
     * Deletes an access token given a user and the token hash.
     *
     * @param user the user
     * @param tokenHash the token hash
     */
    fun deleteByUserAndTokenHash(user: User, tokenHash: String)

    /**
     * Checks if an access token exists given a user and the token hash.
     *
     * @param user the user
     * @param tokenHash the token hash
     *
     * @return true if the refresh token exists, false otherwise
     */
    fun existsByUserAndTokenHash(user: User, tokenHash: String): Boolean

    /**
     * Deletes all access tokens that have expired.
     *
     * @param expirationDate the expiration date
     *
     * @return the number of deleted tokens
     */
    fun deleteAllByExpirationDateBefore(expirationDate: Timestamp)
}
