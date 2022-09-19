package pt.isel.daw.battleships.database.model.shot

import pt.isel.daw.battleships.database.model.Game
import pt.isel.daw.battleships.database.model.Player
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
class ShotPK(
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    val game: Game,

    @ManyToOne
    @JoinColumn(name = "player", nullable = false)
    val player: Player,

    @Column(name = "round", nullable = false)
    val round: Int
) {
    // Needed for JPA
    constructor() : this(Game(), Player(), 0)
}
