package com.github.virtualstack.repositories

import java.sql.Timestamp
import javax.inject.Inject

import com.github.virtualstack.models.User
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future


class UserRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Users = TableQuery[UserTable]

  def all(): Future[Seq[User]] = db.run(Users.result)

  def insert(user: User): Future[Unit] = db.run(Users += user).map { _ => () }

  def insert(users: Seq[User]): Future[Unit] = db.run(this.Users ++= users).map(_ => ())

  def findById(id: Long): Future[Option[User]] = db.run(Users.filter(_.id === id).result.headOption)

  def update(id: Long, user: User): Future[Unit] = {
    val userToUpdate: User = user.copy(Some(id))
    db.run(Users.filter(_.id === id).update(userToUpdate)).map(_ => ())
  }

  def delete(id: Long): Future[Unit] =
    db.run(Users.filter(_.id === id).delete).map(_ => ())

  def count(): Future[Int] = {
    db.run(Users.map(_.id).length.result)
  }

  private class UserTable(tag: Tag) extends Table[User](tag, "users") {

    implicit val dateColumnType = MappedColumnType.base[DateTime, Long](d => d.getMillis, d => new DateTime(d))

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def password = column[String]("password")
    def gitbucketToken = column[String]("gitbucket_token")
    def createdAt = column[DateTime]("created_at")
    def updatedAt = column[DateTime]("updated_at")

    def * = (id.?, name, password, gitbucketToken, createdAt, updatedAt) <> (User.tupled, User.unapply _)
  }

}
