package pt.isel.daw.battleships

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pt.isel.daw.battleships.pipeline.AuthenticationInterceptor

/**
 * The main entry point of the Spring application.
 *
 * @property authInterceptor The authentication interceptor.
 */
@SpringBootApplication
class BattleshipsApplication(
    val authInterceptor: AuthenticationInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/games/**")
    }
}

fun main(args: Array<String>) {
    runApplication<BattleshipsApplication>(*args)
//    val ctx: ApplicationContext = runApplication<BattleshipsApplication>(*args)
//    println("Let's inspect the beans provided by Spring Boot:")
//    val beanNames = ctx.beanDefinitionNames
//    beanNames.sort()
//    beanNames.forEach { println(it) }
}
