package pt.isel.daw.battleships.http.siren

import org.springframework.http.MediaType
import pt.isel.daw.battleships.http.media.siren.Action
import pt.isel.daw.battleships.http.media.siren.Link
import pt.isel.daw.battleships.http.media.siren.SubEntity
import java.net.URI
import kotlin.test.Test

class SubEntityTests {

    @Test
    fun `EmbeddedLink creation is successful`() {
        SubEntity.EmbeddedLink(
            `class` = listOf("class"),
            rel = listOf("rel"),
            href = URI("http://localhost"),
            type = MediaType.APPLICATION_JSON,
            title = "title"
        )
    }

    @Test
    fun `EmbeddedLink creation with null fields is successful`() {
        SubEntity.EmbeddedLink(
            `class` = null,
            rel = listOf("rel"),
            href = URI("http://localhost"),
            type = null,
            title = null
        )
    }

    @Test
    fun `EmbeddedSubEntity creation is successful`() {
        SubEntity.EmbeddedSubEntity(
            `class` = listOf("class"),
            rel = listOf("rel"),
            properties = "properties",
            entities = listOf(
                SubEntity.EmbeddedLink(
                    `class` = listOf("class"),
                    rel = listOf("rel"),
                    href = URI("http://localhost"),
                    type = MediaType.APPLICATION_JSON,
                    title = "title"
                )
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
            )
        )
    }

    @Test
    fun `EmbeddedSubEntity creation with null fields is successful`() {
        SubEntity.EmbeddedSubEntity(
            `class` = null,
            rel = listOf("rel"),
            properties = null,
            entities = null,
            actions = null,
            links = null
        )
    }
}
