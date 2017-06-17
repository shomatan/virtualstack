package com.github.virtualstack.controllers.api.v1

import javax.inject.{Inject, Singleton}

import com.github.virtualstack.models.User
import com.github.virtualstack.repositories.UserRepository
import org.joda.time.DateTime
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object UserJsonController {
  case class UserForm(
    id: Option[Long],
    name: String,
    password: String,
    gitbucketToken: String,
    createdAt: Option[DateTime],
    updatedAt: DateTime
  )

  implicit val userJodaDateReads = Reads.jodaDateReads("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  implicit val userJodaDateWrites = Writes.jodaDateWrites("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

  implicit val userFormFormat = (
      (__ \ "id"            ).readNullable[Long]    and
      (__ \ "name"          ).read[String]          and
      (__ \ "password"      ).read[String]          and
      (__ \ "gitbucketToken").read[String]          and
      (__ \ "createdAt").readNullable[DateTime]     and
      (__ \ "updatedAt").read[DateTime]
    )(UserForm)

  implicit val usersRowWritesWrites = (
      (__ \ "id"            ).writeNullable[Long] and
      (__ \ "name"          ).write[String]       and
      (__ \ "password"      ).write[String]       and
      (__ \ "gitbucketToken").write[String]       and
      (__ \ "createdAt").write[DateTime]          and
      (__ \ "updatedAt").write[DateTime]
    )(unlift(User.unapply))
}

@Singleton
class UserController @Inject()(userRepository: UserRepository) extends Controller {

  import UserJsonController._

  /** Describe the user form (used in both edit and create screens).*/
  val userForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "name" -> nonEmptyText,
      "password" -> nonEmptyText,
      "gitbucketToken" -> nonEmptyText,
      "createdAt" -> optional(jodaDate),
      "updatedAt" -> jodaDate
    )(UserForm.apply)(UserForm.unapply))

  def add = Action.async(parse.json) { implicit request =>
    Logger.debug("In add user")

    request.body.validate[UserForm].map { form =>

      val user = User(
        name = form.name, password = form.password, gitbucketToken = form.gitbucketToken, updatedAt = form.updatedAt)

      Logger.debug(user.toString)

      userRepository.insert(
        user
      ).map(_ => Ok(Json.obj("result" -> "success")))
    }.recoverTotal { e =>
      Future {
        BadRequest(Json.obj("result" ->"failure", "error" -> JsError.toJson(e)))
      }
    }
  }
}
