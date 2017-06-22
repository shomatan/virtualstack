package com.github.virtualstack.controllers.api.v1.auth

import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration
import play.api.Configuration
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsError, Json}
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.mvc.{Action, Controller}
import net.ceedubs.ficus.Ficus.{finiteDurationReader, optionValueReader, toFicusConfig}
import com.mohiva.play.silhouette.api.{LoginEvent, Silhouette}
import com.mohiva.play.silhouette.api.Authenticator.Implicits.RichDateTime
import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.util.{Clock, Credentials}
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import com.github.virtualstack.services.UserService
import com.github.virtualstack.utils.authentication.DefaultEnv

class SignInController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  userService: UserService,
  //authInfoRepository: AuthInfoRepository,
  credentialsProvider: CredentialsProvider,
  configuration: Configuration,
  clock: Clock) extends Controller with I18nSupport {

  implicit val jsonFormat = Json.format[SignInData]

  case class SignInData(
    email: String,
    password: String
  )

  def submit = Action.async(parse.json) { implicit request =>

    request.body.validate[SignInData].fold(
      errors => {
        Future.successful(Unauthorized(Json.obj("message" -> Messages("invalid.data"), "errors" -> JsError.toJson(errors))))
      },
      data => {
        credentialsProvider.authenticate(Credentials(data.email, data.password)).flatMap { loginInfo =>
          userService.retrieve(loginInfo).flatMap {
            case Some(user) => silhouette.env.authenticatorService.create(loginInfo).map {
              case authenticator => authenticator
            }.flatMap { authenticator =>
              silhouette.env.eventBus.publish(LoginEvent(user, request))
              silhouette.env.authenticatorService.init(authenticator).map { token =>
                Ok(Json.obj("token" -> token))
              }
            }
            case None => Future.failed(new IdentityNotFoundException("Couldn't find user"))
          }
        }.recover {
          case e: ProviderException =>
            Unauthorized(Json.obj("message" -> Messages("invalid.credentials")))
        }
      })
  }

}
