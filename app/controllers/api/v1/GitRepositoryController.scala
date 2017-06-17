package com.github.virtualstack.controllers.api.v1

import javax.inject.{Inject, Singleton}

import com.github.virtualstack.gitbucket.client.GitbucketClient
import com.github.virtualstack.gitbucket.client.Repository

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class GitRepositoryController @Inject()(client: GitbucketClient) extends Controller {

  implicit val repositoryWrites = Json.writes[Repository]

  def all() = Action.async { implicit request =>

    client.getRepositories().map { aa =>
      Ok(Json.toJson(aa))
    }
  }
}
