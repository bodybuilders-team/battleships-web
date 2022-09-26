package pt.isel.daw.battleships.database.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "games")
class Game(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player1", nullable = false)
    val player1: Player?,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player2", nullable = false)
    val player2: Player?,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "current_player_turn", nullable = false)
    val currentPlayerTurn: Player?,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "winner", nullable = false)
    val winner: Player?,

    @Column(name = "current_round", nullable = false)
    val currentRound: Int,

    @Column(name = "state", nullable = false)
    val state: Int
)
