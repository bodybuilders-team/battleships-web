package pt.isel.daw.battleships.database.model.ship

import pt.isel.daw.battleships.database.model.Coordinate
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "ships")
class Ship(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "orientation", nullable = false)
    val orientation: String,

    @Embedded
    val cordinate: Coordinate,

    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    val type: ShipType,

    @Column(name = "lives", nullable = false)
    val lives: Int
) {
    // Needed for JPA
    constructor() : this(0, "", Coordinate(), ShipType(), 0)
}
