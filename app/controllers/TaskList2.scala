package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel

@Singleton
class TaskList2 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def load: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.TaskList2())
  }
  def login = Action {
    Ok(views.html.Login2())
  }
  def validate(username: String, password: String) = Action {
    if (TaskListInMemoryModel.validateUser(username, password)) {
      Ok(views.html.TaskList2TaskView(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    }
    else {
      Ok(views.html.Login2())
    }
  }
  def create(username: String, password: String) = Action {
    if (TaskListInMemoryModel.createUser(username, password)) {
      Ok(views.html.TaskList2TaskView(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    }
    else {
      Ok(views.html.Login2())
    }
  }
  def delete(index: Int): Action[AnyContent] = Action { implicit request =>
    request.session.get("username").map { username =>
      TaskListInMemoryModel.removeTask(username, index)
      Ok(views.html.TaskList2TaskView(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.Login2()))
  }
  def addTask(task: String) = Action { implicit request =>
    request.session.get("username").map { username =>
      TaskListInMemoryModel.addTask(username, task)
      Ok(views.html.TaskList2TaskView(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.Login2()))
  }
  def logout = Action {
    Redirect(routes.TaskList2.load).withNewSession
  }
}
