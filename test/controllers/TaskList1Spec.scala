package controllers

import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, PlaySpec}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

class TaskList1Spec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory{
  "Task List 1" must {
    "login and access functions" in {
      go to s"http://localhost:$port/login1"
      pageTitle mustBe "Login 1"
      click on "username-login"
      textField("username-login").value = "Felix"
      click on "password-login"
      pwdField("password-login").value = "password"
      submit()
      eventually {
        println("In eventually")
        pageTitle mustBe "Task List"
      }
    }
  }
}
