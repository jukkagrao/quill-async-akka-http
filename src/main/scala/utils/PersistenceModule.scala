package utils

import io.getquill._
import persistence.dal.{SuppliersDal, SuppliersDalImpl}

trait DbContext {
  val ctx: PostgresAsyncContext[SnakeCase]
}

trait PersistenceModule {
  val suppliersDal: SuppliersDal
}


trait PersistenceModuleImpl extends PersistenceModule with DbContext {
  this: Configuration =>

  override lazy val ctx = new PostgresAsyncContext[SnakeCase](SnakeCase, "quill")

  override val suppliersDal = new SuppliersDalImpl(ctx)

}
