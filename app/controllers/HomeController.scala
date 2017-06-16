package controllers

import javax.inject._
import play.api._
import play.api.mvc._


import com.github.virtualstack.gitbucket.client._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>

    val client = new GitbucketClient

    println(client.getPublicRepositories.head.name)
    println(client.getPublicRepositories.head.fullName)

    Ok(client.getPublicRepositories.head.toString)
  }
}
