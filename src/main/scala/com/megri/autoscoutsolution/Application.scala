package com.megri.autoscoutsolution

import fs2._
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.StreamApp

object Application extends StreamApp {
  override def stream(args: List[String]): Stream[Task, Nothing] = {
    val storage = new InMemoryStorage

    val service = new Service(storage)

    BlazeBuilder
      .bindHttp(8080, "localhost")
      .mountService(service.routes)
      .serve
  }
}

