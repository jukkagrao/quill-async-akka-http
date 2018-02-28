package persistence.entities

import java.util.UUID

case class Supplier(id : UUID, name: String, description: String) {
  def toSimpleSupplier = SimpleSupplier(this.name,this.description)
}

case class SimpleSupplier(name: String, desc: String){
  def toSupplier = Supplier(UUID.randomUUID(),this.name,this.desc)
}