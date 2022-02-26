package controllers

import models.TaskListInMemoryModel
import play.api.mvc.{AbstractController, BaseController, ControllerComponents, MessagesAbstractController, MessagesControllerComponents}
import play.api.data._
import play.api.data.Forms._

import javax.inject.{Inject, Singleton}

case class LoginData(username: String, password: String)

@Singleton
class taskList1 @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc){

  val loginForm = Form(mapping(
    "Username" -> text(3, 10),
    "Password" -> text(8)
  )(LoginData.apply)(LoginData.unapply))

  def login = Action { implicit request =>
    Ok(views.html.login1(loginForm))
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"$username logged into $password")
  }

  def validateLoginPost = Action { implicit request =>
    val postVal = request.body.asFormUrlEncoded
    postVal.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)) {
        Redirect(routes.taskList1.taskList).withSession("username" -> username)
      }
      else Redirect(routes.taskList1.login).flashing("error" -> "Invalid username/password combination")
    }.getOrElse(Redirect(routes.taskList1.login))
  }
  def validateLoginForm = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.login1(formWithErrors)),
      ld =>
          if (TaskListInMemoryModel.createUser(ld.username, ld.password)) {
              Redirect(routes.taskList1.taskList).withSession("username" -> ld.username)
          }
          else Redirect(routes.taskList1.login).flashing("error" -> "User creation failed")
    )
  }

  def createUser = Action { implicit request =>
    val postVal = request.body.asFormUrlEncoded
    postVal.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Redirect(routes.taskList1.taskList).withSession("username" -> username)
      }
      else Redirect(routes.taskList1.login).flashing("error" -> "User creation failed")
    }.getOrElse(Redirect(routes.taskList1.login))
  }

  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTasks(username)
      Ok(views.html.taskList1(tasks))
    }.getOrElse(Redirect(routes.taskList1.login))
  }

  def addTask = Action { implicit request =>
    val postVal = request.body.asFormUrlEncoded
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      postVal.map { args =>
        val task = args("newTask").head
        TaskListInMemoryModel.addTask(username, task)
        Redirect(routes.taskList1.taskList)
      }.getOrElse(Redirect(routes.taskList1.taskList))
    }.getOrElse(Redirect(routes.taskList1.login))
  }

  def deleteTask = Action { implicit request =>
    val postVal = request.body.asFormUrlEncoded
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      postVal.map { args =>
        val i = args("index").head.toInt
        TaskListInMemoryModel.removeTask(username, i)
        Redirect(routes.taskList1.taskList)
      }.getOrElse(Redirect(routes.taskList1.taskList))
    }.getOrElse(Redirect(routes.taskList1.login))
  }

  def logout = Action {
    Redirect(routes.taskList1.login).withNewSession
  }
}
