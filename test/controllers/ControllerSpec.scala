package controllers

import controllers.Application
import org.scalatestplus.play.PlaySpec
import play.api.test.{FakeRequest, Helpers}
import play.api.test.Helpers._

class ControllerSpec extends PlaySpec {
  "Application#index" must {
    "give back expected page" in {
      val controller  = new Application(Helpers.stubControllerComponents())
      val result = controller.index().apply(FakeRequest())
      val bodyText = contentAsString(result)
      bodyText must include ("Home")
    }
  }
}