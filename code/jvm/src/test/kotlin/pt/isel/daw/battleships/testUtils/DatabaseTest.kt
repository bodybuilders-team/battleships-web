package pt.isel.daw.battleships.testUtils

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(classes = [TestConfig::class])
@AutoConfigureTestEntityManager
@Transactional(rollbackFor = [Exception::class])
abstract class DatabaseTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    /**
     * Clears the database before each test.
     * This is done by running the script `cleanData.sql` located in the sql folder.
     */
    @BeforeEach
    fun resetDatabase() {
        entityManager.runScript("..\\sql\\createSchema.sql")
    }
}
