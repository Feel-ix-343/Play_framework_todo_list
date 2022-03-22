package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel

@Singleton
class TaskList2 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def load: Action[AnyContent] = Action { implicit request =>
    request.session.get("username").map{ username =>
      Ok(views.html.TaskList2(routes.TaskList2.taskList.toString))
    }.getOrElse(Ok(views.html.TaskList2(routes.TaskList2.login.toString)))
  }
  def taskList = Action { implicit request =>
    request.session.get("username").map { username =>
      Ok(views.html.TaskList2TaskView(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.Login2()))
  }
  def login = Action {
    Ok(views.html.Login2())
  }
  def validate = Action { implicit request =>
    val postVal = request.body.asFormUrlEncoded
    postVal.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)) {
        Ok(views.html.TaskList2TaskView(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
      }
      else {
        Ok(views.html.Login2())
      }
    }.getOrElse(Ok(views.html.Login2()))

  }
  def create = Action { implicit request =>
    val postVal = request.body.asFormUrlEncoded
    postVal.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Ok(views.html.TaskList2TaskView(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
      }
      else {
        Ok(views.html.Login2())
      }
    }.getOrElse(Ok(views.html.Login2()))
  }
  def delete: Action[AnyContent] = Action { implicit request =>
    val postVal = request.body.asFormUrlEncoded
    postVal.map { args =>
      val index = args("index").head.toInt
        request.session.get("username").map { username =>
          TaskListInMemoryModel.removeTask(username, index)
          Ok(views.html.TaskList2TaskView(TaskListInMemoryModel.getTasks(username)))
      }.getOrElse(Ok(views.html.Login2()))
    }.getOrElse(Ok(views.html.Login2()))
  }
  def addTask = Action { implicit request =>
    val postVal = request.body.asFormUrlEncoded
    postVal.map { args =>
      val task = args("task").head
      request.session.get("username").map { username => 
        TaskListInMemoryModel.addTask(username, task)
        Ok(views.html.TaskList2TaskView(TaskListInMemoryModel.getTasks(username)))
      }.getOrElse(Ok(views.html.Login2()))
    }.getOrElse(Ok(views.html.Login2()))
  }
  def logout = Action {
    Redirect(routes.TaskList2.load).withNewSession
  }
}
