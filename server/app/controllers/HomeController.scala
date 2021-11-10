package controllers

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import db.domain.Common.CreateUser
import db.domain.{User, UserWithoutId}
import org.webjars.play.WebJarsUtil
import play.api.Configuration
import play.api.libs.json.{Json, OFormat, Reads, __}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import views.html._

import javax.inject._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               val configuration: Configuration,
                               implicit val webJarsUtil: WebJarsUtil,
                               indexTemplate: index,
                               gameTemplate: views.html.game.index,
                               @Named("user-manager") val userManager: ActorRef
)(implicit val ec: ExecutionContext)
  extends BaseController
    with LazyLogging {

  implicit val defaultTimeout: Timeout = Timeout(10.seconds)

  val user: UserWithoutId = UserWithoutId("Martin", "Odersky")
  implicit val userFormat: OFormat[User]   = Json.format[User]

  def index = Action(Ok(indexTemplate()))

  def game: Action[AnyContent] = Action(Ok(gameTemplate()))


  def createUser: Action[AnyContent] = Action.async { implicit request =>
    (userManager ? CreateUser(user))
      .mapTo[User].map { user =>
      Ok(Json.toJson(user))
    }
      .recover { case err =>
        println(s"Error: $err")
        BadRequest("Error")
      }

  }

}
