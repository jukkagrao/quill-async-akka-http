package utils



import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import rest.SupplierRoutes

object SwaggerDocService extends SwaggerHttpService {
  override val apiClasses: Set[Class[_]]= Set(classOf[SupplierRoutes])
  override val host = "localhost:8080"
  override val info = Info(version = "2.0")

  def assets: Route = pathPrefix("swagger") {
    getFromResourceDirectory("swagger") ~ pathSingleSlash(get(redirect("index.html", StatusCodes.PermanentRedirect))) }

}
