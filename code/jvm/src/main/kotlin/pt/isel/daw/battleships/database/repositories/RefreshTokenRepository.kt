package pt.isel.daw.battleships.database.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import pt.isel.daw.battleships.database.model.RefreshToken
import pt.isel.daw.battleships.database.model.User

/**
 * Repository for the [RefreshToken] entity.
 */
interface RefreshTokenRepository : JpaRepository<RefreshToken, Int> {

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
     * Gets the oldest refresh token for a given user.
     *
     * @param user the user
     * @param pageable the page
     *
     * @return the oldest refresh token for the user
     */
    @Query("SELECT rt2 FROM RefreshToken rt2 WHERE rt2.user=:user ORDER BY rt2.expirationDate ASC")
    fun getOldestRefreshTokensByUser(user: User, pageable: Pageable): Page<RefreshToken>
}
