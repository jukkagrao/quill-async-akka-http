package persistence

import persistence.entities.SimpleSupplier
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object JsonProtocol extends DefaultJsonProtocol {
  implicit val supplierFormat: RootJsonFormat[SimpleSupplier] = jsonFormat2(SimpleSupplier)
}