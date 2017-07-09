package com.github.virtualstack.gitbucket.client.services

trait HttpService {
  val httpClient: HttpClient = HttpClient
}

object HttpClient extends HttpClient

trait HttpClient {

  import dispatch._
  import Defaults._

  val baseUrl = "http://localhost:8081/api/v3"
  val token = "a2a1821e817f1e557dcfe37202b18fddcd75b866"

  def get(endpoint: String): Future[String] = {
    val request = url(baseUrl + endpoint)
      .GET
      .setContentType("application/json", "UTF-8")
      .setHeader("Authorization", s"token ${token}")
    Http(request OK as.String)
  }

  def post(endpoint: String): String = {
    ""
  }
}