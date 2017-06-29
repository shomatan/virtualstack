package com.github.virtualstack.controllers

import play.api.mvc._
import play.api.routing._

class JsRouter extends Controller {

  def javascriptRoutes = Action { implicit request =>
    import routes.javascript._
    import api.v1.routes.javascript._
    import api.v1.auth.routes.javascript._
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        HomeController.index,
        HomeController.signOut,
        HomeController.profile,
        ImageRepositoryController.all,
        ImageRepositoryController.detail,
        GitRepositoryController.all,
        SignInController.submit,
        SignUpController.submit
      )
    ).as("text/javascript")
  }

}