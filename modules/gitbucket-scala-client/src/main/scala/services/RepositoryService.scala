package com.github.virtualstack.gitbucket.client.services

import com.github.virtualstack.gitbucket.client.{HttpService, Repository}
import io.circe._
import io.circe.generic.auto._

trait RepositoryService extends HttpService {

  def getPublicRepositories(): List[Repository] = {
    parser.decode[List[Repository]](httpClient.get("/user/repos")).right.get
  }

}
