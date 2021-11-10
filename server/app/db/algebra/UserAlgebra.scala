package db.algebra

import cats.effect._
import db.sql.UserSql._
import db.domain.{User, UserWithoutId}
import skunk._

trait UserAlgebra[F[_]]{
  def create(user: UserWithoutId): F[User]

  def findAll: F[List[User]]
}

object UserAlgebra {

  def apply[F[_]: Async](session: Resource[F, Session[F]]): UserAlgebra[F] =
    new UserAlgebra[F] {
      def create(user: UserWithoutId): F[User] =
        session.use(_.prepare(insert).use(_.unique(user)))

      override def findAll: F[List[User]] =
        session.use(_.execute(selectAll))
    }

}
