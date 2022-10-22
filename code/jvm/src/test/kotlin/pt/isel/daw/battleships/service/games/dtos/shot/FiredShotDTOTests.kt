package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.domain.Coordinate
import pt.isel.daw.battleships.domain.Player
import pt.isel.daw.battleships.domain.Shot
import pt.isel.daw.battleships.domain.User
import pt.isel.daw.battleships.domain.game.Game
import pt.isel.daw.battleships.domain.game.GameConfig
import pt.isel.daw.battleships.domain.game.GameState
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import java.sql.Timestamp
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class FiredShotDTOTests {

    @Test
    fun `OutputShotDTO creation is successful`() {
        FiredShotDTO(
            coordinate = CoordinateDTO(col = 'A', row = 1),
            round = 1,
            result = ShotResultDTO(result = "HIT")
        )
    }

    @Test
    fun `OutputShotDTO from Shot conversion is successful`() {
        val user = User(username = "username", email = "email", passwordHash = "password", points = 0)
        val player = Player(
            game = Game(
                name = "game",
                creator = user,
                config = GameConfig(
                    gridSize = 1,
                    maxTimePerRound = 2,
                    maxTimeForLayoutPhase = 4,
                    shotsPerRound = 3,
                    shipTypes = listOf()
                ),
                state = GameState(
                    phase = GameState.GamePhase.WAITING_FOR_PLAYERS,
                    phaseEndTime = Timestamp(Instant.now().epochSecond),
                    round = 1,
                    turn = null,
                    winner = null
                )
            ),
            user = user,
            points = 0
        )

        val shot = Shot(
            player = player,
            coordinate = Coordinate(col = 'A', row = 1),
            round = 1,
            result = Shot.ShotResult.HIT
        )
        val firedShotDTO = FiredShotDTO(shot)

        assertEquals(CoordinateDTO(shot.coordinate), firedShotDTO.coordinate)
        assertEquals(shot.round, firedShotDTO.round)
        assertEquals(ShotResultDTO(shot.result), firedShotDTO.result)
    }
}
