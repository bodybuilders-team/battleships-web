package pt.isel.daw.battleships.database.model

import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.ship.Ship
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * The Player entity.
 *
 * @property id the id of the player
 * @property game the game that the player is playing
 * @property user the user that is playing the game
 * @property points the points that the player has
 * @property ships the ships that the player has
 * @property shots the shots that the player has made
 */
@Entity
@Table(name = "players")
class Player(
    @ManyToOne
    @JoinColumn(name = "game", nullable = false)
    val game: Game,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "points", nullable = false)
    val points: Int = 0
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player")
    val ships: MutableList<Ship> = mutableListOf()

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player")
    val shots: MutableList<Shot> = mutableListOf()
}
