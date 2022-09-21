package pt.isel.daw.battleships.api.users

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.database.model.User

@RestController
@RequestMapping("/users")
class UsersController {

    @GetMapping("/")
    fun getUsers(): List<User> {
        // TODO: To be implemented
    }

    @PostMapping("/")
    fun createUser(): User {
        // TODO: To be implemented
    }

    @GetMapping("/{username}")
    fun getUser(): User {
        // TODO: To be implemented
    }
}
