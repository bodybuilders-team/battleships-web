package pt.isel.daw.battleships.database.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import pt.isel.daw.battleships.database.model.RefreshToken
import pt.isel.daw.battleships.database.model.User

interface RefreshTokenRepository : JpaRepository<RefreshToken, Int> {

    fun findByUserAndTokenHash(user: User, tokenHash: String): RefreshToken?

    fun countByUser(user: User): Int

    fun deleteByUserAndTokenHash(user: User, tokenHash: String)

    fun existsByUserAndTokenHash(user: User, tokenHash: String): Boolean

    @Query("SELECT rt2 FROM RefreshToken rt2 WHERE rt2.user=:user ORDER BY rt2.expirationDate ASC")
    fun getOldestRefreshTokensByUser(user: User, pageable: Pageable): Page<RefreshToken>
}
