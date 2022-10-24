package pt.isel.daw.battleships.testUtils

import org.hibernate.Session
import org.hibernate.query.NativeQuery
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.io.File

fun TestEntityManager.runScript(filepath: String) {
    val session: Session = entityManager.delegate as Session
    val sql = File(filepath).readText()
    val query: NativeQuery<*> = session.createNativeQuery(sql)
    query.executeUpdate()
}
