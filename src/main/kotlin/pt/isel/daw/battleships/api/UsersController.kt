package pt.isel.daw.battleships.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.database.model.User

@RestController
@RequestMapping("/users")
class UsersController {

    @GetMapping("/", produces = ["text/main"])
    fun getUsers(): List<User> {
        // TODO: To be implemented
        return listOf()
    }

    @GetMapping("/{username}", produces = ["text/main"])
    fun getUser(): User {
        // TODO: To be implemented
        return User()
    }
}
