import com.typesafe.config.{Config, ConfigFactory}
import sbt.File

import scala.util.Try

object Common {
  lazy val appProperties: Config = ConfigFactory.parseFile(new File("src/main/resources/application.conf"))

  lazy val databaseUrl: String = Try(s"jdbc:postgresql://${appProperties.getString("quill.host")}:" +
    s"${appProperties.getString("quill.port")}/${appProperties.getString("quill.database")}").getOrElse("jdbc://")
  lazy val databaseUser: String = Try(appProperties.getString("quill.user")).getOrElse("")
  lazy val databasePassword: String = Try(appProperties.getString("quill.password")).getOrElse("")

}
