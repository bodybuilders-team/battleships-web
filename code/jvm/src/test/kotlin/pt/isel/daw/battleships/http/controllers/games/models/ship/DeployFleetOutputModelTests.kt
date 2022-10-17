package pt.isel.daw.battleships.http.controllers.games.models.ship

import kotlin.test.Test

class DeployFleetOutputModelTests {

    @Test
    fun `DeployFleetOutputModel creation is successful`() {
        DeployFleetOutputModel(successfullyDeployed = true)
    }
}
