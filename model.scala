//> using dep "tech.neander::smithy4s-deriving::0.0.2"
//> using platform scala-js

import smithy4s.*, deriving.{given, *}, aliases.*

import scala.annotation.experimental // the derivation of API uses experimental metaprogramming features, at this time.
import scala.scalajs.js.Promise

case class Response(headers: Map[String, String], origin: String, url: String)
    derives Schema
