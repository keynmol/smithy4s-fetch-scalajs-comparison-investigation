//> using dep com.disneystreaming.smithy4s::smithy4s-http4s::0.18.22
//> using dep "tech.neander::smithy4s-deriving::0.0.2"
//> using dep org.http4s::http4s-dom::0.2.11
//> using platform scala-js
//> using scala 3.5.0-RC1
//> using option -Wunused:all

package test.http4s

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.http4s.dom.FetchClientBuilder
import org.http4s.implicits.uri
import smithy4s.*
import smithy4s.http4s.SimpleRestJsonBuilder

import scala.annotation.experimental

import deriving.{given, *}
import aliases.*

case class Response(headers: Map[String, String], origin: String, url: String)
    derives Schema

@experimental
@simpleRestJson
trait IOService derives API:
  @readonly
  @httpGet("/get")
  def get(): IO[Response]

@experimental
@main def test =
  val httpClient = FetchClientBuilder[IO].create
  SimpleRestJsonBuilder(API.service[IOService])
    .client[IO](httpClient)
    .uri(uri"https://httpbin.org")
    .resource
    .map(_.unliftService)
    .use(serv => serv.get().flatMap(IO.println))
    .unsafeToPromise()
