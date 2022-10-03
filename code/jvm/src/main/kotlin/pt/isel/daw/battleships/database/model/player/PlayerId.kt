package pt.isel.daw.battleships.database.model.player

import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
class PlayerId(
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    val game: Game,

    @ManyToOne
    @JoinColumn(name = "username")
    val user: User
) : Serializable {
    companion object {
        private const val serialVersionUID = 6876151078995664452L
    }
}
