//> using dep "tech.neander::smithy4s-deriving::0.0.2"
//> using dep "tech.neander::smithy4s-fetch::0.0.2-SNAPSHOT"
//> using platform scala-js

import smithy4s.*, deriving.{given, *}, aliases.*

import scala.annotation.experimental // the derivation of API uses experimental metaprogramming features, at this time.
import scala.scalajs.js.Promise
import concurrent.ExecutionContext.Implicits.global

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

  service.get().toFuture.foreach(println)
