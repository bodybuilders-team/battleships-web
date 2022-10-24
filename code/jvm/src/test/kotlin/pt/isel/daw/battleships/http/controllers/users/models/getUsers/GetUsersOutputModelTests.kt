package pt.isel.daw.battleships.http.controllers.users.models.getUsers

import kotlin.test.Test

class GetUsersOutputModelTests {

    @Test
    fun `GetUsersOutputModel creation is successful`() {
        GetUsersOutputModel(
            totalCount = 1
        )
    }

    companion object {
        val defaultGetUsersOutputModel
            get() = GetUsersOutputModel(
                totalCount = 1
            )
    }
}
