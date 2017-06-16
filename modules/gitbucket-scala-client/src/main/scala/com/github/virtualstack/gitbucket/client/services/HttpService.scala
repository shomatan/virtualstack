package com.github.virtualstack.gitbucket.client.services

trait HttpService {
  val httpClient: HttpClient = HttpClient
}

object HttpClient extends HttpClient

trait HttpClient {

  import dispatch._
  import Defaults._

  val baseUrl = "http://localhost:8081/api/v3"

  def get(endpoint: String): String = {
    val request = url(baseUrl + endpoint)
      .GET
      .setContentType("application/json", "UTF-8")
      .setHeader("Authorization", "token 458fe9979f939e2d73fbc594b5109a3b993ecf4e")
    val response = Http(request).apply()
    response.getResponseBody
  }

  def post(endpoint: String): String = {
    ""
  }
}