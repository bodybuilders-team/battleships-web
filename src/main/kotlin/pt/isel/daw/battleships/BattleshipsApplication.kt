package pt.isel.daw.battleships

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * The main entry point of the Spring application.
 */
@SpringBootApplication
class BattleshipsApplication

fun main(args: Array<String>) {
    runApplication<BattleshipsApplication>(*args)
}
