package pt.isel.daw.battleships.database.model.ship

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

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
