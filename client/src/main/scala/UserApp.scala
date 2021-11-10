import japgolly.scalajs.react.component.Scala.{BackendScope, Component}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{CtorType, ScalaComponent}
import org.scalajs.dom.document
import org.scalajs.dom.html.Div

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("User")
class UserApp extends AjaxImplicits {

  type AppComponentType = Component[Unit, State, Backend, CtorType.Nullary]

  case class State()

  class Backend($: BackendScope[Unit, State]) {

    def render(implicit state: State): VdomTagOf[Div] =
      <.div("User page!")
  }

  val AppComponent: AppComponentType =
    ScalaComponent.builder[Unit]
      .initialState(State())
      .renderBackend[Backend]
      .build

  AppComponent().renderIntoDOM(document.getElementById("app"))
}
