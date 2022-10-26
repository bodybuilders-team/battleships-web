package pt.isel.daw.battleships.http.pipeline.authentication

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AuthenticatedTests {

    @Test
    fun `Authenticated has FUNCTION and CLASS as its targets (Target Annotation)`() {
        val targetAnnotation = Authenticated::class.annotations.find { it is Target } as? Target

        assertNotNull(targetAnnotation)
        assertEquals(
            listOf(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS),
            targetAnnotation.allowedTargets.toList()
        )
    }
}
