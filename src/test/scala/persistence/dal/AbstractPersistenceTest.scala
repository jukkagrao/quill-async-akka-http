package persistence.dal


import io.getquill.{PostgresAsyncContext, SnakeCase}
import org.flywaydb.core.Flyway
import org.flywaydb.core.internal.util.jdbc.DriverDataSource
import org.scalatest.{FunSuite, Suite}
import utils._

import scala.util.Try

trait AbstractPersistenceTest extends FunSuite {
  this: Suite =>

  trait Modules extends ConfigurationModuleImpl with TestPersistenceModule {}

  trait TestPersistenceModule extends PersistenceModule with DbContext {
    this: Configuration =>

    val configPrefix = "quilltest"

    override lazy val ctx = new PostgresAsyncContext[SnakeCase](SnakeCase, configPrefix)
    override val suppliersDal: SuppliersDal = new SuppliersDalImpl(ctx)
//    val self: TestPersistenceModule = this

    def onStart(): Unit = {
      val url = Try {
        s"jdbc:postgresql://${config.getString(s"$configPrefix.host")}:" +
          s"${config.getString(s"$configPrefix.port")}/${config.getString(s"$configPrefix.database")}"
      }.getOrElse("")
      val user = Try(config.getString(s"$configPrefix.user")).getOrElse("")
      val password = Try(config.getString(s"$configPrefix.password")).getOrElse("")

      val flyway = new Flyway
      flyway.setDataSource(new DriverDataSource(this.getClass.getClassLoader, "org.postgresql.Driver", url, user, password))
      flyway.setLocations("filesystem:migrations/src/main/resources/db/migration")
      flyway.clean()
      flyway.migrate()

    }

    onStart()

  }

}

