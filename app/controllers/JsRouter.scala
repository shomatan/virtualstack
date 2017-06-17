package com.github.virtualstack.controllers

import play.api.mvc._
import play.api.routing._

class JsRouter extends Controller {

  def javascriptRoutes = Action { implicit request =>
    import api.v1.routes.javascript._
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        ImageRepositoryController.all,
        ImageRepositoryController.detail,
        GitRepositoryController.all,
        UserController.add
      )
    ).as("text/javascript")
  }

}