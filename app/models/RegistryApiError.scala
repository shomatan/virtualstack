package com.github.virtualstack.models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


case class RegistryApiContent(code: String, message: String, detail: Map[String, String])

object RegistryApiContent {
  implicit def jsonWrites = Json.writes[RegistryApiContent]
  implicit def jsonReads = Json.reads[RegistryApiContent]
}

case class RegistryApiError(errors: List[RegistryApiContent])

object RegistryApiError {
  implicit def jsonWrites = Json.writes[RegistryApiError]
  implicit def jsonReads = Json.reads[RegistryApiError]
}