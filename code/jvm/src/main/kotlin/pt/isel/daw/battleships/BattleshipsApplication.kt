package pt.isel.daw.battleships

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pt.isel.daw.battleships.http.pipeline.authentication.AuthenticationInterceptor

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
        registry.addInterceptor(/* interceptor = */ authInterceptor)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowCredentials(true)
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedOrigins("http://localhost:3000", "http://localhost")
    }
}

/**
 * Configuration for the request logging filter.
 */
@Configuration
class RequestLoggingFilterConfig {

    /**
     * Creates a request logging filter.
     *
     * @return the request logging filter
     */
    @Bean
    fun logFilter() = CommonsRequestLoggingFilter().also {
        it.setIncludeClientInfo(true)
        it.setIncludeQueryString(true)
        it.setIncludePayload(true)
        it.setIncludeHeaders(true)
        it.setMaxPayloadLength(10000)
        it.setAfterMessagePrefix("REQUEST DATA : ")
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
