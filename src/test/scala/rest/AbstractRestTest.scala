package rest

import akka.actor.ActorSystem
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{Matchers, WordSpec}
import org.specs2.mock.Mockito
import persistence.dal.SuppliersDal
import utils.{ActorModule, ConfigurationModuleImpl, PersistenceModule}

trait AbstractRestTest extends WordSpec with Matchers with ScalatestRouteTest with Mockito {

  trait Modules extends ConfigurationModuleImpl with ActorModule with PersistenceModule  {
    val system: ActorSystem = AbstractRestTest.this.system

    override val suppliersDal: SuppliersDal = mock[SuppliersDal]
  }

  def getConfig: Config = ConfigFactory.empty()

}
