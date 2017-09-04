package com.megri.autoscoutsolution.model

import io.circe._
import io.circe.generic.extras.semiauto._

sealed trait FuelType

object FuelType {
  object Diesel extends FuelType

  object Gasoline extends FuelType

  implicit val decoder: Decoder[FuelType] = deriveEnumerationDecoder

  implicit val encoder: Encoder[FuelType] = deriveEnumerationEncoder
}
