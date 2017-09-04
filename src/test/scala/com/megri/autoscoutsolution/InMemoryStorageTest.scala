package com.megri.autoscoutsolution

import com.megri.autoscoutsolution.model.{CarAdvert, FuelType, IncomingCarAdvert}
import org.scalatest.{MustMatchers, WordSpec}

class InMemoryStorageTest extends WordSpec with MustMatchers {
  "InMemoryStorage" when {
    "inserting records" should {
      "insert items in order, starting with 0" in {
        val storage = new InMemoryStorage

        storage.create(IncomingCarAdvert("A", FuelType.Gasoline, 1000))
        storage.create(IncomingCarAdvert("B", FuelType.Gasoline, 2000))
        storage.create(IncomingCarAdvert("C", FuelType.Gasoline, 3000))

        storage.readAll mustBe Seq(
          CarAdvert(0, "A", FuelType.Gasoline, 1000),
          CarAdvert(1, "B", FuelType.Gasoline, 2000),
          CarAdvert(2, "C", FuelType.Gasoline, 3000)
        )
      }
    }

    "updating an existing record" should {
      "replace the record" in {
        val storage = new InMemoryStorage

        val createdCarAdvert = storage.create(IncomingCarAdvert("A", FuelType.Gasoline, 1000))

        storage.update(createdCarAdvert.id, IncomingCarAdvert("B", FuelType.Diesel, 2000))

        storage.readAll mustBe Seq(CarAdvert(createdCarAdvert.id, "B", FuelType.Diesel, 2000))
      }
    }

    "updating arbitrary records" should {
      "allow insertion at arbitrary IDs" in {
        val storage = new InMemoryStorage

        storage.update(42, IncomingCarAdvert("A", FuelType.Gasoline, 1000))
  
        storage.readAll mustBe Seq(
          CarAdvert(42, "A", FuelType.Gasoline, 1000)
        )
      }

      "advance the internal counter after insertion" in {
        val storage = new InMemoryStorage

        storage.update(42, IncomingCarAdvert("A", FuelType.Gasoline, 1000))
        storage.create(IncomingCarAdvert("B", FuelType.Gasoline, 2000))

        storage.readAll mustBe Seq(
          CarAdvert(42, "A", FuelType.Gasoline, 1000),
          CarAdvert(43, "B", FuelType.Gasoline, 2000)
        )
      }
    }

    "removing records" should {
      "yield None when deleting a non-existant ID" in {
        val storage = new InMemoryStorage

        val result = storage.delete(1)

        result mustBe None
      }

      "allow removal of existing IDs" in {
        val storage = new InMemoryStorage

        val createdCarAdvert = storage.create(IncomingCarAdvert("A", FuelType.Gasoline, 1000))
        val deletedCarAdvert = storage.delete(createdCarAdvert.id)

        deletedCarAdvert mustBe Some(createdCarAdvert)
      }
    }
  }
}
