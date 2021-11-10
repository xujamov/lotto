package db.domain

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import User.{LastName, UserId, UserName}
case class User (
  id: UserId,
  name: UserName,
  lastName: Option[LastName] = None
)

object User {
  type UserId = Int
  type UserName = String
  type LastName = String

  implicit val userDecoder: Decoder[User] = deriveDecoder[User]
  implicit val userEncoder: Encoder[User] = deriveEncoder[User]
}