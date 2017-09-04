package com.megri.autoscoutsolution.model

import java.time.LocalDate

import io.circe._
import io.circe.syntax._
import io.circe.generic.semiauto._
import io.circe.java8.time._

case class CarAdvert(
  id: Long,
  title: String,
  fuel: FuelType,
  price: Int,
  mileage: Option[Int] = None,
  firstRegistration: Option[LocalDate] = None
) {
  def isNew: Boolean = mileage.isEmpty && firstRegistration.isEmpty
}

object CarAdvert {
  implicit val jsonEncoder: ObjectEncoder[CarAdvert] = {
    val derivedEncoder = deriveEncoder[CarAdvert]

    ObjectEncoder.instance[CarAdvert]{carAdvert =>
      derivedEncoder.encodeObject(carAdvert)
        .add("new", carAdvert.isNew.asJson)
    }
  }
}
