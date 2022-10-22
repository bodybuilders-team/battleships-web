package pt.isel.daw.battleships.domain.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@TestConfiguration
class TestConfig(
    @Value("\${spring.testDatasource.url}")
    val dbUrl: String,
    @Value("\${spring.testDatasource.username}")
    val dbUsername: String,
    @Value("\${spring.testDatasource.password}")
    val dbPassword: String
) {

    @Bean
    fun dataSource(): DataSource? {
        val dataSource = DriverManagerDataSource()

        dataSource.setDriverClassName("org.postgresql.Driver")
        dataSource.url = dbUrl
        dataSource.username = dbUsername
        dataSource.password = dbPassword
        return dataSource
    }
}
