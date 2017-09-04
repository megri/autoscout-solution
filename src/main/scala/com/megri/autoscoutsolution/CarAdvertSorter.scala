package com.megri.autoscoutsolution

import java.time.LocalDate

import com.megri.autoscoutsolution.model.{CarAdvert, FuelType}
import org.http4s._

sealed abstract class CarAdvertSorter(val fieldName: String) {
  def sort(items: Seq[CarAdvert], descending: Boolean = false): Seq[CarAdvert]
}

object CarAdvertSorter {
  def apply[A](fieldName: String, f: CarAdvert => A)(implicit ordering: Ordering[A]): CarAdvertSorter =
    new CarAdvertSorter(fieldName) {
      override def sort(items: Seq[CarAdvert], descending: Boolean): Seq[CarAdvert] =
        if (descending)
          items.sortBy(f)(ordering.reverse)
        else
          items.sortBy(f)(ordering)
    }

  implicit val fuelTypeOrdering: Ordering[FuelType] = Ordering.by(_.getClass.getSimpleName)

  implicit val localDateOrdering: Ordering[LocalDate] = _ compareTo _

  val sortOptions = Seq(
    CarAdvertSorter("id", _.id),
    CarAdvertSorter("title", _.title),
    CarAdvertSorter("fuel", _.fuel),
    CarAdvertSorter("price", _.price),
    CarAdvertSorter("mileage", _.mileage),
    CarAdvertSorter("firstRegistration", _.firstRegistration)
  )

  val defaultSorter = CarAdvertSorter("id", _.id)

  implicit val qpd: QueryParamDecoder[CarAdvertSorter] =
    QueryParamDecoder.stringQueryParamDecoder
      .map(value => sortOptions.find(_.fieldName equalsIgnoreCase value).getOrElse(defaultSorter))
}
