package model

import models.TaskListInMemoryModel
import org.scalatestplus.play.PlaySpec

class TaskListInMemoryModelSpec extends PlaySpec{
  "TaskListInMemoryModel" must {
    "do valid login for default user" in {
      TaskListInMemoryModel.validateUser("Felix", "password") mustBe true
    }

    "reject login with wrong password" in {
      TaskListInMemoryModel.validateUser("Felix", "pass") mustBe false
    }

    "reject login with wrong username" in {
      TaskListInMemoryModel.validateUser("Mike", "pass") mustBe false
    }

    "reject login with wrong username and password" in {
      TaskListInMemoryModel.validateUser("Mike", "passwod") mustBe false
    }

    "get correct default tasks" in {
      TaskListInMemoryModel.getTasks("Felix") mustBe List("Make videos", "eat", "sleep", "code")
    }

    "create new user with no tasks" in {
      TaskListInMemoryModel.createUser("Mike", "password") mustBe true
      TaskListInMemoryModel.getTasks("Mike") mustBe Nil
    }

    "create new user with existing name" in {
      TaskListInMemoryModel.createUser("Felix", "paword") mustBe false
    }

    "add new task for default user" in {
      TaskListInMemoryModel.addTask("Felix", "testing")
      TaskListInMemoryModel.getTasks("Felix") must contain("testing")
    }

    "add new task for new user" in {
      TaskListInMemoryModel.addTask("Mike", "testing1")
      TaskListInMemoryModel.getTasks("Mike") must contain("testing1")
    }

    "remove task from default user" in {
      TaskListInMemoryModel.removeTask("Felix", TaskListInMemoryModel.getTasks("Felix").indexOf("eat"))
      TaskListInMemoryModel.getTasks("Felix") must not contain "eat"
    }
  }
}
