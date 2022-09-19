package pt.isel.daw.battleships.database.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "players")
class Player(
    @Id
    @ManyToOne
    @JoinColumn(name = "username")
    val user: User,

    @OneToOne(mappedBy = "player1")
    val game_id: Game,

    @Column(name = "points", nullable = false)
    val points: Int
) {
    // Needed for JPA
    constructor() : this(User(), Game(), 0)
}
