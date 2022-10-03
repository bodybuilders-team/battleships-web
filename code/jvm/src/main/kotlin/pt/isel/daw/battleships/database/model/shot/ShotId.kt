package pt.isel.daw.battleships.database.model.shot

import pt.isel.daw.battleships.database.model.player.Player
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne

/**
 * Represents the primary key of the [Shot] entity.
 *
 * @property player The player who made the shot.
 * @property round The round in which the shot was made.
 * @property shotNumber The number of the shot in the round.
 */
@Embeddable
class ShotId(
    @ManyToOne
    @JoinColumns(
        JoinColumn(name = "game_id", referencedColumnName = "game_id", insertable = false, updatable = false),
        JoinColumn(name = "player", referencedColumnName = "username", insertable = false, updatable = false)
    )
    val player: Player,

    @Column(name = "round", nullable = false)
    val round: Int,

    @Column(name = "shot_number", nullable = false)
    val shotNumber: Int

) : Serializable
