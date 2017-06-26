package com.github.virtualstack.controllers.api.v1

import javax.inject.{Inject, Singleton}

import com.github.virtualstack.models.{RegistryApiError, Repository, Tag}
import com.github.virtualstack.utils.HttpRequest
import com.github.virtualstack.utils.authentication.DefaultEnv

import com.mohiva.play.silhouette.api.Silhouette
import play.api.libs.ws._
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

@Singleton
class ImageRepositoryController @Inject()(
  ws: WSClient,
  val silhouette: Silhouette[DefaultEnv]) extends Controller {

  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext

  // DTO
  case class RepositoriesResponse(repositories: List[String])
  case class TagsResponse(name: String, tags: List[String])

  // Tag
  implicit val tagReads = Json.reads[Tag]
  implicit val tagWrites = Json.writes[Tag]

  implicit val repoResponseReads = Json.reads[RepositoriesResponse]
  implicit val tagResponseReads = Json.reads[TagsResponse]

  // Repository
  implicit def repositoryWrites = Json.writes[Repository]

  def all = silhouette.SecuredAction.async { implicit request =>

    val complexRequest = HttpRequest.create(ws, "_catalog")

    complexRequest.get().map { response =>
      val convertedJson = response.json.validate[RepositoriesResponse]

      val repositories = convertedJson.get match {
        case RepositoriesResponse(t) => t.map { c => Repository(name = c, tag = None, tags = None) }
        case _  => List.empty[Repository]
      }

      Ok(Json.toJson(repositories))
    }
  }

  def detail(name: String) = Action.async { implicit request =>

    val complexRequest = HttpRequest.create(ws, s"${name}/tags/list")

    complexRequest.get().map { response =>

      // Check the API error response
      response.json.validate[RegistryApiError] match {
        case t: JsSuccess[RegistryApiError] => InternalServerError(Json.obj("result" ->"failure", "message" -> t.get.errors))
        case _ => {
          // Check json
          response.json.validate[TagsResponse] match {
            case s: JsSuccess[TagsResponse] => {
              val tags = s.get.tags.map { name => Tag(name) }
              Ok(Json.toJson(Repository(name = name, tag = None, tags = Some(tags))))
            }
            case e: JsError => InternalServerError(Json.obj("result" ->"failure", "message" -> JsError.toJson(e)))
          }
        }
      }
    }.recover {
      case e => ServiceUnavailable(Json.obj("result" ->"failure", "message" -> e.getMessage))
    }
  }

}
