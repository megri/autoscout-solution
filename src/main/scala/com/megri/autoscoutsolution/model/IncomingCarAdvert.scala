package com.megri.autoscoutsolution.model

import java.time.LocalDate

import io.circe._
import io.circe.generic.semiauto._
import io.circe.java8.time._

case class IncomingCarAdvert(
  title: String,
  fuel: FuelType,
  price: Int,
  mileage: Option[Int],
  firstRegistration: Option[LocalDate]
) {
  def withId(id: Long): CarAdvert =
    CarAdvert(id, title, fuel, price, mileage, firstRegistration)
}

object IncomingCarAdvert {
  implicit val jsonDecoder: Decoder[IncomingCarAdvert] = deriveDecoder
}
