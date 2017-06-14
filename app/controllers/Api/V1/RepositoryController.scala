package com.github.virtualstack.controllers.Api.V1

import javax.inject.{Inject, Singleton}

import com.github.virtualstack.models.Repository

import play.api.libs.ws._
import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json

@Singleton
class RepositoryController @Inject()(ws: WSClient) extends Controller {

  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext

  case class RepositoriesResponse(repositories: List[String])

  implicit val responseReads = Json.reads[RepositoriesResponse]

  implicit val repositoryWrites = Json.writes[Repository]

  def all = Action.async { implicit request =>

    val complexRequest = createRequest("_catalog")

    complexRequest.get().map { response =>
      val convertedJson = response.json.validate[RepositoriesResponse]

      val repositories = convertedJson.get match {
        case RepositoriesResponse(t) => t.map { c => Repository(c) }
        case _  => List.empty[Repository]
      }

      Ok(Json.toJson(repositories))
    }
  }

  def createRequest(name: String) = {
    // TODO: Make host URL configurable
    ws.url("http://localhost:5000/v2/" + name)
      .withHeaders("Accept" -> "application/json")
  }
}
