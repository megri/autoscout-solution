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
  mileage: Option[Int] = None,
  firstRegistration: Option[LocalDate] = None
)

object CarAdvert {
  implicit val jsonEncoder: ObjectEncoder[CarAdvert] = deriveEncoder
}
