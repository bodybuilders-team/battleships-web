package pt.isel.daw.battleships.api.users

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.battleships.api.users.dtos.CreateUserRequestDTO
import pt.isel.daw.battleships.api.users.dtos.CreateUserResponseDTO
import pt.isel.daw.battleships.api.users.dtos.LoginUserRequestDTO
import pt.isel.daw.battleships.api.users.dtos.LoginUserResponseDTO
import pt.isel.daw.battleships.api.users.dtos.UserDTO
import pt.isel.daw.battleships.services.users.UsersService

@RestController
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {

    @PostMapping
    fun createUser(@RequestBody userData: CreateUserRequestDTO): CreateUserResponseDTO {
        val token = usersService.createUser(userData.toCreateUserRequest())
        return CreateUserResponseDTO(token)
    }

    @PostMapping("/login")
    fun login(@RequestBody userData: LoginUserRequestDTO): LoginUserResponseDTO {
        val token = usersService.login(userData.username, userData.password)
        return LoginUserResponseDTO(token)
    }

    @GetMapping("/{username}")
    fun getUser(@PathVariable username: String): UserDTO? {
        return UserDTO(usersService.getUser(username))
    }
}
