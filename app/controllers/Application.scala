package controllers

import javax.inject._
import play.api._
import play.api.mvc._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Application @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request =>
    Ok(views.html.index())
  }

  def randomNumber = Action {
    Ok(util.Random.nextInt(100).toString)
  }

  def randomString(length: Int) = Action {
    Ok(util.Random.nextString(length))
  }
}
