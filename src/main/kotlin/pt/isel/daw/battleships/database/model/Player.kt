package pt.isel.daw.battleships.database.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
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
    @Id
    @ManyToOne
    @JoinColumn(name = "username")
    val user: User?,

    @OneToOne(mappedBy = "player1")
    val game: Game?,

    @Column(name = "points", nullable = false)
    val points: Int
) : Serializable
