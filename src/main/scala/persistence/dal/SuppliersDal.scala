package persistence.dal

import java.util.UUID

import io.getquill._
import persistence.entities.Supplier

import scala.concurrent.{ExecutionContext, Future}

trait SuppliersDal {
  def insert(supplierToInsert: Supplier)(implicit ec: ExecutionContext): Future[Long]
  def findById(supId: UUID)(implicit ec: ExecutionContext) : Future[Option[Supplier]]
  def delete(supId: UUID)(implicit ec: ExecutionContext): Future[Long]
}

class SuppliersDalImpl(context: PostgresAsyncContext[SnakeCase]) extends SuppliersDal {

  import context._

  override def insert(supplierToInsert: Supplier)(implicit ec: ExecutionContext): Future[Long] = {
    val q = quote {
      query[Supplier].insert(lift(supplierToInsert))
    }
    context.run(q)
  }

  override def findById(supId: UUID)(implicit ec: ExecutionContext) : Future[Option[Supplier]] = {
    val q = quote {
      query[Supplier].filter(_.id == lift(supId))
    }
    context.run(q).map(_.headOption)
  }

  override def delete(supId: UUID)(implicit ec: ExecutionContext): Future[Long] = {
    val q = quote {
      query[Supplier].filter(_.id == lift(supId)).delete
    }
    context.run(q)
  }
}
