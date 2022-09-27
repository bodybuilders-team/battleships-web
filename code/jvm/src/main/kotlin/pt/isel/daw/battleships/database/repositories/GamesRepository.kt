package pt.isel.daw.battleships.database.repositories

import org.springframework.data.repository.CrudRepository
import pt.isel.daw.battleships.database.model.game.Game

/**
 * Repository for the GameResponse entity.
 */
interface GamesRepository : CrudRepository<Game, Int>
