package pt.isel.daw.battleships.database.model.ship

import pt.isel.daw.battleships.database.model.game.Game
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

/**
 * The ShipType entity.
 *
 * @property shipName The name of the ship.
 * @property size The size of the ship.
 * @property points The points of the ship.
 */
@Entity
@Table(name = "ship_types")
class ShipType(
    @Column(name = "ship_name", nullable = false)
    val shipName: String,

    @Column(name = "size", nullable = false)
    val size: Int,

    @Column(name = "quantity", nullable = false)
    val quantity: Int,

    @Column(name = "points", nullable = false)
    val points: Int
) {
    @OneToOne
    @JoinColumn(name = "game", nullable = false)
    var game: Game? = null

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShipType

        if (shipName != other.shipName) return false
        if (size != other.size) return false
        if (quantity != other.quantity) return false
        if (points != other.points) return false

        return true
    }
}
