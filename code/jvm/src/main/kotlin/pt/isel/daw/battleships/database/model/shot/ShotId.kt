package pt.isel.daw.battleships.database.model.shot

import pt.isel.daw.battleships.database.model.Player
import pt.isel.daw.battleships.database.model.game.Game
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 * Represents the primary key of the [Shot] entity.
 *
 * @property game The game in which the shot was made.
 * @property player The player who made the shot.
 * @property round The round in which the shot was made.
 * @property shotNumber The number of the shot in the round.
 */
@Embeddable
class ShotId(
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    val game: Game,

    @ManyToOne
    @JoinColumn(name = "player", nullable = false)
    val player: Player,

    @Column(name = "round", nullable = false)
    val round: Int,

    @Column(name = "shot_number", nullable = false)
    val shotNumber: Int
) : Serializable {
    companion object {
        private const val SERIAL_VERSION_UID = 5592698679692612632L
    }
}
