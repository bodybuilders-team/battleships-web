package pt.isel.daw.battleships.testUtils

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@TestConfiguration
class TestConfig(
    @Value("\${spring.test-datasource.url}")
    val dbUrl: String,

    @Value("\${spring.test-datasource.username}")
    val dbUsername: String,

    @Value("\${spring.test-datasource.password}")
    val dbPassword: String
) {

    /**
     * Configures the data source to be used by the tests.
     *
     * @return the data source to be used by the tests
     */
    @Bean
    fun dataSource(): DataSource? = DriverManagerDataSource()
        .also {
            it.setDriverClassName("org.postgresql.Driver")
            it.url = dbUrl
            it.username = dbUsername
            it.password = dbPassword
        }
}
