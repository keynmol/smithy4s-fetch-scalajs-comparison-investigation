//> using dep com.disneystreaming.smithy4s::smithy4s-http4s::0.18.22
//> using dep org.http4s::http4s-dom::0.2.11
//> using platform scala-js

import smithy4s.*, deriving.{given, *}, aliases.*

import scala.annotation.experimental // the derivation of API uses experimental metaprogramming features, at this time.
import cats.effect.IO
import smithy4s.http4s.SimpleRestJsonBuilder
import org.http4s.dom.FetchClientBuilder
import org.http4s.implicits.uri
import cats.effect.unsafe.implicits.global

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
