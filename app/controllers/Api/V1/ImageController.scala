package controllers

import javax.inject.{Inject, Singleton}

import com.github.virtualstack.models.Image
import com.github.virtualstack.models.RegistryApiError
import com.github.virtualstack.utils.HttpRequest

import play.api.libs.ws._
import play.api.mvc.{Action, Controller}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

@Singleton
class ImageController @Inject()(ws: WSClient) extends Controller {

  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext

  implicit val imageWrites = Json.writes[Image]
  implicit val imageReads: Reads[Image] = (
      (JsPath \ "name").read[String] and
      (JsPath \ "tag").read[String]
    )(Image.apply _)

  def detail(name: String) = Action.async { implicit request =>

    val complexRequest = HttpRequest.create(ws, s"${name}/manifests/latest")

    complexRequest.get().map { response =>

      // Check the API error response
      response.json.validate[RegistryApiError] match {
        case t: JsSuccess[RegistryApiError] => Ok(Json.obj("message" -> t.get.errors))
        case _ => {
          // Check json
          response.json.validate[Image] match {
            case s: JsSuccess[Image] => Ok(Json.toJson(s.get))
            case e: JsError => InternalServerError(Json.obj("message" -> JsError.toJson(e)))
          }
        }
      }
    }.recover {
      case e => ServiceUnavailable(Json.obj("message" -> e.getMessage))
    }
  }
}
