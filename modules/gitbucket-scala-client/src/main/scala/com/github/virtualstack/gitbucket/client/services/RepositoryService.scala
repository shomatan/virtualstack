package com.github.virtualstack.gitbucket.client.services

import com.github.virtualstack.gitbucket.client.Repository
import io.circe._
import dispatch._, Defaults._


trait RepositoryService extends HttpService {

  def getRepositories(): Future[List[Repository]] = {
    val response = httpClient.get("/user/repos")
    for( res <- response)
    yield parser.decode[List[Repository]](res).right.get
  }

}
