package com.github.virtualstack.models

import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import org.joda.time.DateTime

case class User (
  userId: UUID,
  loginInfo: LoginInfo,
  firstName: Option[String],
  lastName: Option[String],
  fullName: Option[String],
  email: Option[String],
  passwordInfo: Option[PasswordInfo],
  createdAt: DateTime = DateTime.now(),
  updatedAt: DateTime
)
