package com.github.virtualstack.utils

import play.api.libs.ws._

object HttpRequest {

  def create(ws: WSClient, name: String) = {
    // TODO: Make host URL configurable
    ws.url("http://localhost:5000/v2/" + name)
      .withHeaders("Accept" -> "application/json")
  }
}