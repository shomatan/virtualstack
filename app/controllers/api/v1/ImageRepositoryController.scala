package com.github.virtualstack.controllers.api.v1

import javax.inject.{Inject, Singleton}

import com.github.virtualstack.models.Repository
import com.github.virtualstack.models.RegistryApiError
import com.github.virtualstack.utils.HttpRequest

import play.api.libs.ws._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.mvc.{Action, Controller}

@Singleton
class ImageRepositoryController @Inject()(ws: WSClient) extends Controller {

  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext

  case class RepositoriesResponse(repositories: List[String])

  implicit val responseReads = Json.reads[RepositoriesResponse]

  implicit val repositoryWrites = Json.writes[Repository]
  implicit val repositoryReads: Reads[Repository] = (
        (JsPath \ "name").read[String] and
        (JsPath \ "tag").read[String]
    )(Repository.apply _)

  def all = Action.async { implicit request =>

    val complexRequest = HttpRequest.create(ws, "_catalog")

    complexRequest.get().map { response =>
      val convertedJson = response.json.validate[RepositoriesResponse]

      val repositories = convertedJson.get match {
        case RepositoriesResponse(t) => t.map { c => Repository(name = c) }
        case _  => List.empty[Repository]
      }

      Ok(Json.toJson(repositories))
    }
  }

  def detail(name: String) = Action.async { implicit request =>

    val complexRequest = HttpRequest.create(ws, s"${name}/manifests/latest")

    complexRequest.get().map { response =>

      // Check the API error response
      response.json.validate[RegistryApiError] match {
        case t: JsSuccess[RegistryApiError] => Ok(Json.obj("message" -> t.get.errors))
        case _ => {
          // Check json
          response.json.validate[Repository] match {
            case s: JsSuccess[Repository] => Ok(Json.toJson(s.get))
            case e: JsError => InternalServerError(Json.obj("message" -> JsError.toJson(e)))
          }
        }
      }
    }.recover {
      case e => ServiceUnavailable(Json.obj("message" -> e.getMessage))
    }
  }

}
