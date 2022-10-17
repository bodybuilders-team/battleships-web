package pt.isel.daw.battleships.http.pipeline.authentication

/**
 * Indicates that the annotated handler method requires authentication.
 * If a controller class is annotated with this annotation, all its methods require authentication.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Authenticated
