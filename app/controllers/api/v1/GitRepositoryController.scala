package com.github.virtualstack.controllers.api.v1

import javax.inject.{Inject, Singleton}

import com.github.virtualstack.gitbucket.client.GitbucketClient
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import io.circe.syntax._
import io.circe.generic.auto._

@Singleton
class GitRepositoryController @Inject()(client: GitbucketClient) extends Controller {

  def all() = Action.async { implicit request =>

    client.getRepositories().map { aa =>
      Ok(aa.asJson.noSpaces)
    }
  }
}
