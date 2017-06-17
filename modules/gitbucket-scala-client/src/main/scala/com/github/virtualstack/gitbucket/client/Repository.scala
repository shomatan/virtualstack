package com.github.virtualstack.gitbucket.client

import io.circe._

case class Repository(
   name: String,
   fullName: String,
   description: String,
   `private`: Boolean,
   defaultBranch: String
 )

object Repository {
  implicit val decodeUser: Decoder[Repository] =
    Decoder.forProduct5("name", "full_name", "description", "private", "default_branch")(Repository.apply)
}