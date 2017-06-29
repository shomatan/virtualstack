package com.github.virtualstack.controllers

import javax.inject._

import com.github.virtualstack.models.User
import com.github.virtualstack.utils.authentication.DefaultEnv

import com.mohiva.play.silhouette.api.{LogoutEvent, Silhouette}

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (val silhouette: Silhouette[DefaultEnv]) extends Controller {

  case class UserDTO (firstName: Option[String], lastName: Option[String], fullName: Option[String], email: Option[String])

  implicit val userDTOWrites = (
      (__ \ "firstName").writeNullable[String] and
      (__ \ "lastName" ).writeNullable[String] and
      (__ \ "fullName" ).writeNullable[String] and
      (__ \ "email"    ).writeNullable[String]
    )(unlift(UserDTO.unapply))

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def signOut = silhouette.SecuredAction.async { implicit request =>
    silhouette.env.eventBus.publish(LogoutEvent(request.identity, request))
    silhouette.env.authenticatorService.discard(request.authenticator, Ok)
  }

  def profile = silhouette.SecuredAction { implicit request =>

    val user = UserDTO(
      request.identity.firstName,
      request.identity.lastName,
      request.identity.fullName,
      request.identity.email
    )
    Ok(Json.toJson(user))
  }
}
