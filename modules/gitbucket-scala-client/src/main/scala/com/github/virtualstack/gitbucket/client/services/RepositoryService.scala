package com.github.virtualstack.gitbucket.client.services

import com.github.virtualstack.gitbucket.client.Repository
import io.circe._

trait RepositoryService extends HttpService {

  def getPublicRepositories(): List[Repository] = {
    parser.decode[List[Repository]](httpClient.get("/user/repos")).right.get
  }

}
