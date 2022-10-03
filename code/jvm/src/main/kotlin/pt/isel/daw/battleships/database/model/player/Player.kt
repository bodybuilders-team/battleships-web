package pt.isel.daw.battleships.database.model.player

import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.ship.Ship
import pt.isel.daw.battleships.database.model.shot.Shot
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
 * @property user The user that is playing the game.
 * @property game The game that the player is playing.
 * @property points The points that the player has.
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
    private val ships: MutableList<Ship> = mutableListOf()

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player")
    private val shots: MutableList<Shot> = mutableListOf()
}
