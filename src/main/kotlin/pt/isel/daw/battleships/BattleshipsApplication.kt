package pt.isel.daw.battleships

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BattleshipsApplication

fun main(args: Array<String>) {
    runApplication<BattleshipsApplication>(*args)
}
