package pt.isel.daw.battleships.database.model.shot

import pt.isel.daw.battleships.database.model.Game
import pt.isel.daw.battleships.database.model.Player
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

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
        private const val serialVersionUID = 5592698679692612632L
    }
}
