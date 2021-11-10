import Protocol._
import japgolly.scalajs.react.component.Scala.{BackendScope, Component}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, CtorType, ScalaComponent}
import org.scalajs.dom.document
import org.scalajs.dom.html.{Button, Div}

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("Game")
class Game extends AjaxImplicits {

  type AppComponentType = Component[Unit, State, Backend, CtorType.Nullary]
  case class State(prizes: List[Prize] = Nil)

  class Backend($: BackendScope[Unit, State]) {

    def getAllPrize: Callback =
      get(Urls.GetPrize)
        .fail(onError)
        .done[List[Prize]] { prizes =>
          $.modState(_.copy(prizes = prizes))
        }.asCallback

    def playButton(implicit state: State): VdomTagOf[Button] =
      <.button(^.cls := "btn btn-primary btn-md", ^.onClick --> getAllPrize)("Get Free Prize")

    def prizeList(prize: Prize): TagMod =
      <.li(
        <.img(^.src := prize.image, ^.alt := "")
      )

    def gameRoulette(implicit state: State): TagMod =
      <.div(
        <.div(^.className := "wraper",
          <.div(^.className := "arrowup"),
          <.div(^.className := "arrowdown"),
          <.div(^.className := "window",
            <.ul(^.className := "list")(state.prizes map prizeList: _*)
          )
        ),
        <.p(^.className := "text-center",
          <.button(^.className := "button", "Кнопка")
        ),
        <.div(^.className := "win",
          <.ul
        )
      ).when(state.prizes.nonEmpty)


    def render(implicit state: State): VdomTagOf[Div] =
      <.div(playButton, gameRoulette)
  }

  val AppComponent: AppComponentType =
    ScalaComponent.builder[Unit]
      .initialState(State())
      .renderBackend[Backend]
      .build

  AppComponent().renderIntoDOM(document.getElementById("app-game"))
}
