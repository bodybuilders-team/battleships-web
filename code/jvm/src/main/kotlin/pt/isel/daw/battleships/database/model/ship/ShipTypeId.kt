package pt.isel.daw.battleships.database.model.ship

import pt.isel.daw.battleships.database.model.game.Game
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
class ShipTypeId(
    @Column(name = "ship_name")
    val shipName: String
) : Serializable {

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    var game: Game? = null

    companion object {
        private const val serialVersionUID = 4404991642099278090L
    }
}
