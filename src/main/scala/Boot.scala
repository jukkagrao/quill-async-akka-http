import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.RouteConcatenation
import akka.stream.ActorMaterializer
import rest.SupplierRoutes
import utils._

import scala.concurrent.ExecutionContextExecutor

object Main extends App with RouteConcatenation with CorsSupport {
  // configuring modules for application, cake pattern for DI
  val modules = new ConfigurationModuleImpl with ActorModuleImpl with PersistenceModuleImpl
  implicit val system: ActorSystem = modules.system
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = modules.system.dispatcher

  val swaggerService = SwaggerDocService

  val bindingFuture = Http().bindAndHandle(
    new SupplierRoutes(modules).routes ~
      swaggerService.assets ~
      corsHandler(swaggerService.routes), "localhost", 8080)

  println(s"Server online at http://localhost:8080/")

}