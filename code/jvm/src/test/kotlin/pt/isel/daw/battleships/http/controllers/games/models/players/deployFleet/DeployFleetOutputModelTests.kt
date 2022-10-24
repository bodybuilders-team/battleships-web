package pt.isel.daw.battleships.http.controllers.games.models.players.deployFleet

import kotlin.test.Test

class DeployFleetOutputModelTests {

    @Test
    fun `DeployFleetOutputModel creation is successful`() {
        DeployFleetOutputModel(successfullyDeployed = true)
    }

    companion object {
        val defaultDeployFleetOutputModel
            get() = DeployFleetOutputModel(successfullyDeployed = true)
    }
}
