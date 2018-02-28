package persistence.dal

import java.util.UUID

import persistence.entities.Supplier
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout

class SuppliersDALTest extends FunSuite with AbstractPersistenceTest with BeforeAndAfterAll {

  implicit val timeout: Timeout = Timeout(5.seconds)
  lazy val generatedId: UUID = UUID.randomUUID()
  val modules: Modules = new Modules {}

  test("SuppliersActor: Testing Suppliers DAL") {
    val numberOfEntities : Long = Await.result(modules.suppliersDal.insert(Supplier(generatedId, "sup", "desc")), 5.seconds)
    assert (numberOfEntities == 1)
    val supplier : Option[Supplier] = Await.result(modules.suppliersDal.findById(generatedId),5.seconds)
    assert (supplier.isDefined &&  supplier.get.name.compareTo("sup") == 0)
    val empty : Option[Supplier] = Await.result(modules.suppliersDal.findById(UUID.randomUUID()),5.seconds)
    assert (empty.isEmpty)
  }

  override def  afterAll() {
    Await.result(modules.suppliersDal.delete(generatedId),5.seconds)
  }

}