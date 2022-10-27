package pt.isel.daw.battleships.domain.games.ship

import pt.isel.daw.battleships.domain.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.domain.games.game.Game
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
class ShipType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "ship_name", nullable = false)
    val shipName: String

    @Column(name = "quantity", nullable = false)
    val quantity: Int

    @Column(name = "size", nullable = false)
    val size: Int

    @Column(name = "points", nullable = false)
    val points: Int

    @OneToOne
    @JoinColumn(name = "game", nullable = false)
    var game: Game? = null

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(
        shipName: String,
        quantity: Int,
        size: Int,
        points: Int
    ) {
        if (shipName.length !in MIN_SHIP_NAME_LENGTH..MAX_SHIP_NAME_LENGTH) {
            throw InvalidShipTypeException(
                "Ship type name must be between $MIN_SHIP_NAME_LENGTH and $MAX_SHIP_NAME_LENGTH characters long"
            )
        }

        if (quantity !in MIN_SHIP_QUANTITY..MAX_SHIP_QUANTITY) {
            throw InvalidShipTypeException(
                "Ship type quantity must be between $MIN_SHIP_QUANTITY and $MAX_SHIP_QUANTITY"
            )
        }

        if (size !in MIN_SHIP_SIZE..MAX_SHIP_SIZE) {
            throw InvalidShipTypeException(
                "Ship type size must be between $MIN_SHIP_SIZE and $MAX_SHIP_SIZE"
            )
        }

        if (points !in MIN_SHIP_POINTS..MAX_SHIP_POINTS) {
            throw InvalidShipTypeException(
                "Ship type points must be between $MIN_SHIP_POINTS and $MAX_SHIP_POINTS"
            )
        }

        this.shipName = shipName
        this.quantity = quantity
        this.size = size
        this.points = points
    }

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

    companion object {
        private const val MIN_SHIP_NAME_LENGTH = 1
        private const val MAX_SHIP_NAME_LENGTH = 40

        private const val MIN_SHIP_QUANTITY = 0
        private const val MAX_SHIP_QUANTITY = 10

        private const val MIN_SHIP_SIZE = 1
        private const val MAX_SHIP_SIZE = 7

        private const val MIN_SHIP_POINTS = 1
        private const val MAX_SHIP_POINTS = 100
    }
}
