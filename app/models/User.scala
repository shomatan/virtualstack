package models

import org.joda.time.DateTime

case class User (
  id: Option[Long] = None,
  name: String,
  password: String,
  gitbucketToken: String,
  createdAt: DateTime = DateTime.now(),
  updatedAt: DateTime
)
