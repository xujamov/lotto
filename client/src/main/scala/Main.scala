import org.scalajs.dom.document

object Main extends App {
  document.body.id match {
    case "user" => new UserApp()
    case "game" => new Game()
  }
}
