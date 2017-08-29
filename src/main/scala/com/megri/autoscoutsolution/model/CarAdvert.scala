package com.megri.autoscoutsolution.model

import java.time.LocalDate

import io.circe._
import io.circe.generic.semiauto._
import io.circe.java8.time._

case class CarAdvert(
  id: Long,
  title: String,
  fuel: FuelType,
  price: Int,
  mileage: Option[Int],
  firstRegistration: Option[LocalDate]
)

object CarAdvert {
  implicit val jsonEncoder: ObjectEncoder[CarAdvert] = deriveEncoder
}
