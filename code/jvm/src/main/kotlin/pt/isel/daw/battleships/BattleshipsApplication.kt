package pt.isel.daw.battleships

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pt.isel.daw.battleships.pipeline.AuthenticationInterceptor

/**
 * The main entry point of the Spring application.
 *
 * @property authInterceptor the authentication interceptor
 */
@SpringBootApplication
class BattleshipsApplication(
    val authInterceptor: AuthenticationInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/games/**")
    }
}

/**
 * The main entry point of the application.
 *
 * @param args the command line arguments
 */
fun main(args: Array<String>) {
    runApplication<BattleshipsApplication>(*args)
}
