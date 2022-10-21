package pt.isel.daw.battleships.database

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import pt.isel.daw.battleships.database.utils.TestConfig
import pt.isel.daw.battleships.database.utils.runScript

@SpringBootTest(classes = [TestConfig::class])
@AutoConfigureTestEntityManager
@Transactional
abstract class DatabaseTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @BeforeEach
    fun clearDatabase() {
        entityManager.runScript("..\\sql\\cleanData.sql")
    }
}
