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
 * @property id the id of the ship type
 * @property shipName the name of the ship
 * @property size the size of the ship
 * @property quantity the quantity of ships of this type
 * @property points the points of the ship
 * @property game the game that the ship type belongs to
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
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @OneToOne
    @JoinColumn(name = "game", nullable = false)
    var game: Game? = null

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

    override fun hashCode(): Int {
        var result = shipName.hashCode()
        result = 31 * result + size
        result = 31 * result + quantity
        result = 31 * result + points
        result = 31 * result + (game?.hashCode() ?: 0)
        result = 31 * result + (id ?: 0)
        return result
    }
}
