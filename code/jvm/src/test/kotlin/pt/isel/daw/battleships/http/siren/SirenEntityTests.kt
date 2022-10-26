package pt.isel.daw.battleships.http.siren

import org.springframework.http.MediaType
import pt.isel.daw.battleships.http.media.siren.Action
import pt.isel.daw.battleships.http.media.siren.Link
import pt.isel.daw.battleships.http.media.siren.SirenEntity
import pt.isel.daw.battleships.http.media.siren.SubEntity
import java.net.URI
import kotlin.test.Test

class SirenEntityTests {

    @Test
    fun `SirenEntity creation is successful`() {
        SirenEntity(
            `class` = listOf("class"),
            properties = "properties",
            entities = listOf(
                SubEntity.EmbeddedLink(
                    `class` = listOf("class"),
                    rel = listOf("rel"),
                    href = URI("http://localhost"),
                    type = MediaType.APPLICATION_JSON,
                    title = "title"
                ),
            ),
            actions = listOf(
                Action(
                    name = "action-name",
                    `class` = listOf("action-class"),
                    method = "action-method",
                    href = URI("http://action-uri"),
                    title = "action-title",
                    type = MediaType.APPLICATION_JSON,
                    fields = listOf(
                        Action.Field(
                            name = "field-name",
                            `class` = listOf("field-class"),
                            type = "field-type",
                            value = "field-value",
                            title = "field-title"
                        )
                    )
                )
            ),
            links = listOf(
                Link(
                    rel = listOf("test"),
                    href = URI("http://localhost:8080/test"),
                    `class` = listOf("test"),
                    title = "test",
                    type = MediaType.APPLICATION_JSON
                )
            ),
            title = "title"
        )
    }

    @Test
    fun `SirenEntity creation with null fields is successful`() {
        SirenEntity(
            `class` = null,
            properties = null,
            entities = null,
            actions = null,
            links = null,
            title = null
        )
    }
}
