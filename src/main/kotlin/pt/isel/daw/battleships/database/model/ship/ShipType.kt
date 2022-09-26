package pt.isel.daw.battleships.database.model.ship

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * The ShipType entity.
 *
 * @property shipName The name of the ship.
 * @property size The size of the ship.
 * @property points The points of the ship.
 */
@Entity
@Table(name = "shiptypes")
class ShipType(
    @Id
    @Column(name = "ship_name")
    val shipName: String,

    @Column(name = "size", nullable = false)
    val size: Int,

    @Column(name = "points", nullable = false)
    val points: Int
)
