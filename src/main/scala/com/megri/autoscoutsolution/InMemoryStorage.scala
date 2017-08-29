package com.megri.autoscoutsolution

import java.util.concurrent.atomic._

import com.megri.autoscoutsolution.model._

class InMemoryStorage {
  private val data = new AtomicReference(Map.empty[Long, CarAdvert])
  private val sequence = new AtomicLong()

  def create(incomingCarAdvert: IncomingCarAdvert): CarAdvert = {
    val id = sequence.getAndIncrement()
    update(id, incomingCarAdvert)
    readUnsafe(id)
  }

  def read(id: Long): Option[CarAdvert] =
    data.get.get(id)

  def readUnsafe(id: Long): CarAdvert =
    data.get.getOrElse(id, throw new Exception(s"Id $id does not exist in storage"))

  def readAll: Seq[CarAdvert] =
    data.get.values.to[Seq]

  def update(id: Long, incomingCarAdvert: IncomingCarAdvert): CarAdvert = {
    sequence.updateAndGet(nextId => if (nextId < id) id + 1 else nextId)
    data.updateAndGet(map => map + (id -> incomingCarAdvert.withId(id)))
    readUnsafe(id)
  }

  def delete(id: Long): Unit = {
    data.getAndUpdate(map => map - id)
  }
}
