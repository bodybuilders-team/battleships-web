package pt.isel.daw.battleships.http.siren

import org.springframework.http.MediaType
import pt.isel.daw.battleships.http.media.siren.Link
import java.net.URI
import kotlin.test.Test

class LinkTests {

    @Test
    fun `Link creation is successful`() {
        Link(
            rel = listOf("test"),
            href = URI("http://localhost:8080/test"),
            `class` = listOf("test"),
            title = "test",
            type = MediaType.APPLICATION_JSON
        )
    }

    @Test
    fun `Link creation with null fields is successful`() {
        Link(
            rel = listOf("test"),
            `class` = null,
            href = URI("http://localhost:8080/test"),
            title = null,
            type = null
        )
    }
}
