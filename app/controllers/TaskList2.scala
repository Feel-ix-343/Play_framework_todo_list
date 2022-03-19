package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel

@Singleton
class TaskList2 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action {
    Ok("Single Page")
  }
}
