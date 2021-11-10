package db.domain

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import User.{LastName, UserName}

case class UserWithoutId(
  name: UserName,
  lastName: LastName
)

object UserWithoutId {
  implicit val usernameDecoder: Decoder[UserWithoutId] = deriveDecoder[UserWithoutId]
  implicit val usernameEncoder: Encoder[UserWithoutId] = deriveEncoder[UserWithoutId]
}

