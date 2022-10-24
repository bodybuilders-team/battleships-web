package pt.isel.daw.battleships.repository.refreshTokens

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import pt.isel.daw.battleships.domain.RefreshToken
import pt.isel.daw.battleships.domain.User

/**
 * Repository for the [RefreshToken] entity.
 */
interface RefreshTokensRepository : JpaRepository<RefreshToken, Int> {

    /**
     * Finds a [RefreshToken] given a user and the token hash.
     *
     * @param user the user
     * @param tokenHash the token hash
     *
     * @return the [RefreshToken] if found, null otherwise
     */
    fun findByUserAndTokenHash(user: User, tokenHash: String): RefreshToken?

    /**
     * Counts the number of refresh tokens for a given user.
     *
     * @param user the user
     * @return the number of refresh tokens for the user
     */
    fun countByUser(user: User): Int

    /**
     * Deletes a refresh token given a user and the token hash.
     *
     * @param user the user
     * @param tokenHash the token hash
     */
    fun deleteByUserAndTokenHash(user: User, tokenHash: String)

    /**
     * Checks if a refresh token exists given a user and the token hash.
     *
     * @param user the user
     * @param tokenHash the token hash
     *
     * @return true if the refresh token exists, false otherwise
     */
    fun existsByUserAndTokenHash(user: User, tokenHash: String): Boolean

    /**
     * Gets the refresh tokens for a given user ordered by expiration date.
     *
     * @param user the user
     * @param pageable the page
     *
     * @return the refresh tokens for the user ordered by expiration date
     */
    @Query("SELECT rt2 FROM RefreshToken rt2 WHERE rt2.user=:user ORDER BY rt2.expirationDate ASC")
    fun getRefreshTokensOfUserOrderedByExpirationDate(user: User, pageable: Pageable): Page<RefreshToken>
}
