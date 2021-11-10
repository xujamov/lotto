package actors

import akka.actor.{Actor, ActorSystem}
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import db.domain.Common.CreateUser
import play.api.{Configuration, Environment}
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.typesafe.scalalogging.LazyLogging
import db.domain.{User, UserWithoutId}

import javax.inject.Inject
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}
import db.module.Database
import protocols.UserProtocol.GetUsers

class UserManager @Inject()(
                             val environment: Environment,
                             implicit val configuration: Configuration,
                           )(implicit val actorSystem: ActorSystem)
  extends Actor with LazyLogging {

  implicit val executionContext: ExecutionContext = context.dispatcher
  implicit val defaultTimeout: Timeout = Timeout(10.seconds)

  override def preStart(): Unit = {
//    (self ? GetStudents).mapTo[List[Student]].map { students =>
//      logger.debug(s"Students: $students")
//    }
  }
  val database: Database[IO] = Database[IO]

  override def receive: Receive = {
//    case GetStudents =>
//      sender() ! getStudents

    case CreateUser(user) =>
      createUser(user).pipeTo(sender())

    case GetUsers =>
      getUsers.pipeTo(sender())

    case other =>
      logger.error(s"CMD not found: [ $other]")
      sender() ! "Cmd not found!"
  }

  def createUser(user: UserWithoutId): Future[User] = {
    database.userAlgebra.flatMap(_.create(user)).unsafeToFuture()
  }

  def getUsers: Future[List[User]] = {
    database.userAlgebra.flatMap(_.findAll).unsafeToFuture()
  }

}
