package com.megri.autoscoutsolution

import com.megri.autoscoutsolution.model._
import fs2.Task
import fs2.interop.cats._
import cats.syntax.flatMap._
import io.circe._
import org.http4s._
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.dsl._

class Service(storage: InMemoryStorage) {
  implicit def entityDecoder[A: Decoder]: EntityDecoder[A] = jsonOf
  implicit def entityEncoder[A: Encoder]: EntityEncoder[A] = jsonEncoderOf

  object CarAdvertSortParam extends OptionalQueryParamDecoderMatcher[CarAdvertSorter]("sortBy")
  object DescSortParam extends OptionalQueryParamDecoderMatcher[Boolean]("desc")

  val routes = HttpService {
    case GET -> Root :? CarAdvertSortParam(maybeSorter) +& DescSortParam(sortDesc) =>
      val data = storage.readAll
      val sorter = maybeSorter.getOrElse(CarAdvertSorter.defaultSorter)
      Ok(sorter.sort(data, descending = sortDesc.getOrElse(false)))

    case GET -> Root / LongVar(id) =>
      storage.read(id) match {
        case Some(carAdvert) => Ok(carAdvert)
        case None => NotFound()
      }

    case request @ POST -> Root =>
      decodeOrReject[IncomingCarAdvert](request, entity => Ok(storage.create(entity)))

    case request @ PUT -> Root / LongVar(id) =>
      decodeOrReject[IncomingCarAdvert](request, entity => Ok(storage.update(id, entity)))

    case DELETE -> Root / LongVar(id) =>
      Ok(storage.delete(id))
  }

  def decodeOrReject[A: Decoder](request: Request, onSuccess: A => Task[Response]): Task[Response] =
    request
      .attemptAs[A]
      .fold(decodeFailure => decodeFailure.toHttpResponse(request.httpVersion), onSuccess)
      .flatten
}
