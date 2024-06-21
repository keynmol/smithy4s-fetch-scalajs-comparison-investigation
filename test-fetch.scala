//> using dep "tech.neander::smithy4s-deriving::0.0.2"
//> using dep "tech.neander::smithy4s-fetch::0.0.3"
//> using platform scala-js
//> using scala 3.5.0-RC1
//> using option -Wunused:all

package test.fetch

import smithy4s.*

import scala.annotation.experimental
import scala.scalajs.js.Promise

import deriving.{given, *}
import aliases.*

case class Response(headers: Map[String, String], origin: String, url: String)
    derives Schema

@experimental
trait PromiseService derives API:
  @readonly
  @httpGet("/get")
  def get(): Promise[Response]

import smithy4s_fetch.*

@experimental
@main def hello =
  val service = SimpleRestJsonFetchClient(
    API.service[PromiseService],
    "https://httpbin.org"
  ).make.unliftService

  service.get().`then`(v => println(v))
