package pt.isel.daw.battleships.utils

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pt.isel.daw.battleships.repository.users.RevokedAccessTokensRepository
import java.sql.Timestamp
import java.time.Instant

@Component
class RevokedAccessTokenScheduler(
    private val revokedAccessTokensRepository: RevokedAccessTokensRepository
) {

    @Scheduled(fixedRate = REVOKED_ACCESS_TOKEN_CLEANUP_INTERVAL)
    @Transactional(rollbackFor = [Exception::class])
    fun removeExpiredTokens() {
        revokedAccessTokensRepository.deleteAllByExpirationDateBefore(Timestamp.from(Instant.now()))
    }

    companion object {
        private const val REVOKED_ACCESS_TOKEN_CLEANUP_INTERVAL = 1000L * 60L * 60L * 8L // Every 8 hours
    }
}
