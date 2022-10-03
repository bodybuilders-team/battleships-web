package pt.isel.daw.battleships.database.model.player

import pt.isel.daw.battleships.database.model.shot.Shot
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * The Player entity.
 *
 * @property user The user that is playing the game.
 * @property game The game that the player is playing.
 * @property points The points that the player has.
 */
@Entity
@Table(name = "players")
class Player(
    @EmbeddedId
    val playerPk: PlayerId,

    @Column(name = "points", nullable = false)
    val points: Int = 0
) {
    //    @OneToMany(cascade = [CascadeType.PERSIST])
//    @JoinColumn(name = "player")
//    private val ships: MutableList<Ship> = mutableListOf()
//
    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player")
    private val shots: MutableList<Shot> = mutableListOf()
}
