package pt.isel.daw.battleships.pipeline.authentication

/**
 * Indicates that the annotated handler method requires authentication.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Authenticated
