package rest

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import persistence.JsonProtocol
import JsonProtocol._
import SprayJsonSupport._
import utils.{Configuration, PersistenceModule}

import scala.util.{Failure, Success}
import io.swagger.annotations._
import javax.ws.rs.Path

import persistence.entities.{Supplier,SimpleSupplier}

import scala.concurrent.ExecutionContext

@Path("/supplier")
@Api(value = "/supplier", produces = "application/json")
class SupplierRoutes(modules: Configuration with PersistenceModule)(implicit ec: ExecutionContext)  extends Directives {

  @Path("/{id}")
  @ApiOperation(value = "Return Supplier", notes = "", nickname = "", httpMethod = "GET")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Supplier Id", required = false, dataType = "int", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return Supplier", response = classOf[Supplier]),
    new ApiResponse(code = 400, message = "The supplier id should be greater than zero"),
    new ApiResponse(code = 404, message = "Return Supplier Not Found"),
    new ApiResponse(code = 500, message = "Internal server error")
  ))
  def supplierGetRoute = path("supplier" / JavaUUID) { (supId) =>
    get {
        onComplete(modules.suppliersDal.findById(supId).mapTo[Option[Supplier]]) {
          case Success(supplierOpt) => supplierOpt match {
            case Some(sup) => complete(sup.toSimpleSupplier)
            case None => complete(NotFound, s"The supplier doesn't exist")
          }
          case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
        }
      }
  }

  @ApiOperation(value = "Add Supplier", notes = "", nickname = "", httpMethod = "POST", produces = "text/plain")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "Supplier Object", required = true,
      dataType = "persistence.entities.SimpleSupplier", paramType = "body")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 500, message = "Internal server error"),
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 201, message = "Entity Created")
  ))
 def supplierPostRoute = path("supplier") {
    post {
      entity(as[SimpleSupplier]) { supplierToInsert => onComplete(modules.suppliersDal.insert(supplierToInsert.toSupplier)) {
        case Success(_) => complete(Created)
        case Failure(ex) => // ex.printStackTrace()
          complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
      }
      }
    }
  }

  val routes: Route = supplierPostRoute ~ supplierGetRoute

}

