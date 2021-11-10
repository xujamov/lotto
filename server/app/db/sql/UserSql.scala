package db.sql

import db.domain.{User, UserWithoutId}
import skunk._
import skunk.codec.all._
import skunk.implicits._

object UserSql {

  val codec: Codec[User] =
    (int4 ~ varchar ~ varchar.opt).imap {
      case i ~ n ~ l => User(i, n, l)
    }(c => c.id ~ c.name ~ c.lastName)

  val insert: Query[UserWithoutId, User] =
    sql"""INSERT INTO "user" VALUES (DEFAULT, $varchar, $varchar) returning id, name, last_name"""
      .query(codec)
      .gcontramap[UserWithoutId]

  val selectAll: Query[Void, User] =
    sql"""SELECT * FROM "user" """.query(codec)
}


