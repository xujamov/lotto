package db.module

import cats.effect.std.Console
import cats.effect.{Async, Sync}
import cats.implicits.catsSyntaxOptionId
import db.algebra.UserAlgebra
import natchez.Trace.Implicits.noop
import skunk.{Session, SessionPool}

trait Database[F[_]] {
  val userAlgebra: F[UserAlgebra[F]]
}

object Database {
  def apply[F[_]: Async: Console](implicit F: Sync[F]): Database[F] = new Database[F] {
    private val session: SessionPool[F] =
      Session.pooled[F](
        host = "localhost",
        port = 5432,
        database = "playexample",
        user = "admin",
        password = "123".some,
        max = 1024)

    override val userAlgebra: F[UserAlgebra[F]] = session.use(s => F.delay(UserAlgebra[F](s)))
  }

}
