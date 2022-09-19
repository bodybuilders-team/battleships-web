package pt.isel.daw.battleships.database.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Id
    @Column(name = "username")
    val username: String,

    @Column(name = "hashed_password", nullable = false)
    val hashedPassword: String,

    @Column(name = "points", nullable = false)
    val points: Int
) {
    // Needed for JPA
    constructor() : this("", "", 0)
}
