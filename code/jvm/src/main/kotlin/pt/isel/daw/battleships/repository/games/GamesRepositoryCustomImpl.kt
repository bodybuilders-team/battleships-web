package pt.isel.daw.battleships.repository.games

import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import pt.isel.daw.battleships.domain.games.Player_
import pt.isel.daw.battleships.domain.games.game.Game
import pt.isel.daw.battleships.domain.games.game.GameConfig
import pt.isel.daw.battleships.domain.games.game.GameState
import pt.isel.daw.battleships.domain.games.game.GameState_
import pt.isel.daw.battleships.domain.games.game.Game_
import pt.isel.daw.battleships.domain.users.User_
import java.util.stream.Stream
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

/**
 * Implementation of the [GamesRepositoryCustom].
 *
 * @property gamesRepository the repository of the games
 */
@Component
class GamesRepositoryCustomImpl(@Lazy val gamesRepository: GamesRepository) : GamesRepositoryCustom {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    data class GamesWithCountResponse(val games: List<Game>, val count: Long)

    override fun findAllAvailableGamesWithConfig(config: GameConfig): Stream<Game> =
        gamesRepository
            .findAllByStatePhase(phase = GameState.GamePhase.WAITING_FOR_PLAYERS)
            .filter { game -> game.config == config }

    override fun findAllWithCount(
        username: String?,
        excludeUsername: String?,
        phases: List<String>?,
        ids: List<Int>?,
        limit: Long,
        offset: Long
    ): GamesWithCountResponse {
        val cb = entityManager.criteriaBuilder

        fun <T> CriteriaQuery<T>.fillWithCustomQuery(root: Root<Game>): CriteriaQuery<T> {
            val predicates = mutableListOf<Predicate>()

            if (username != null || excludeUsername != null) {
                val user = root
                    .join(Game_.players)
                    .join(Player_.user)

                if (username != null)
                    predicates += cb.equal(user.get(User_.username), username)

                if (excludeUsername != null)
                    predicates += cb.notEqual(user.get(User_.username), excludeUsername)
            }

            if (phases != null) {
                predicates +=
                    root.get(Game_.state)
                        .get(GameState_.phase)
                        .`in`(phases.map(GameState.GamePhase::valueOf))
            }

            if (ids != null)
                predicates.add(root.get(Game_.id).`in`(ids))

            return this.where(*predicates.toTypedArray())
                .distinct(true)
        }

        val cq = cb.createQuery(Game::class.java)
        val root = cq.from(Game::class.java)

        val query = entityManager.createQuery(
            cq.fillWithCustomQuery(root)
        )

        query.firstResult = offset.toInt()
        query.maxResults = limit.toInt()

        val countCq = cb.createQuery(Long::class.java)
        val countRoot = countCq.from(Game::class.java)

        val countQuery = entityManager.createQuery(
            countCq
                .select(cb.count(countRoot))
                .fillWithCustomQuery(countRoot)
        )

        return GamesWithCountResponse(
            games = query.resultList,
            count = countQuery.singleResult
        )
    }
}
