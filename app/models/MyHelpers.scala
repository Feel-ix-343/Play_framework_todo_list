package models

object MyHelpers {
  import views.html.helper.FieldConstructor
  implicit val myFields = FieldConstructor(views.html.myFieldConstructorTemplate.f)
}
