package pt.isel.daw.battleships.database.model.ship

import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

/**
 * The ShipType entity.
 *
 * @property shipName The name of the ship.
 * @property size The size of the ship.
 * @property points The points of the ship.
 */
@Entity
@Table(name = "game_shiptypes")
class ShipType(
    @EmbeddedId
    val shipTypeId: ShipTypeId,

    @Column(name = "size", nullable = false)
    val size: Int,

    @Column(name = "quantity", nullable = false)
    val quantity: Int,

    @Column(name = "points", nullable = false)
    val points: Int
)
