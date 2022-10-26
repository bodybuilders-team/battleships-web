package pt.isel.daw.battleships.http.siren

import org.springframework.http.MediaType
import pt.isel.daw.battleships.http.media.siren.Action
import java.net.URI
import kotlin.test.Test

class ActionTests {

    @Test
    fun `Action creation is successful`() {
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
    }

    @Test
    fun `Action creation with null fields is successful`() {
        Action(
            name = "action-name",
            `class` = null,
            method = null,
            href = URI("http://action-uri"),
            title = null,
            type = null,
            fields = null
        )
    }

    @Test
    fun `Field creation is successful`() {
        Action.Field(
            name = "field-name",
            `class` = listOf("field-class"),
            type = "field-type",
            value = "field-value",
            title = "field-title"
        )
    }

    @Test
    fun `Field creation with null fields is successful`() {
        Action.Field(
            name = "field-name",
            `class` = null,
            type = null,
            value = null,
            title = null
        )
    }
}
