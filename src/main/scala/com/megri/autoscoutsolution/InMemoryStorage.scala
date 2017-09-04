package com.megri.autoscoutsolution

import java.util.concurrent.atomic._

import com.megri.autoscoutsolution.model._

class InMemoryStorage {
  private[this] case class State(sequence: Long, data: Map[Long, CarAdvert])
  private[this] val state = new AtomicReference(State(0, Map.empty))

  def create(incomingCarAdvert: IncomingCarAdvert): CarAdvert = {
    val newState = state.updateAndGet{state =>
      val id = state.sequence
      val nextId = id + 1
      val record = id -> incomingCarAdvert.withId(id)
      State(nextId, state.data + record)
    }

    newState.data(newState.sequence - 1)
  }

  def read(id: Long): Option[CarAdvert] = {
    state.get.data.get(id)
  }

  def readAll: Seq[CarAdvert] =
    state.get.data.values.to[Seq]

  def update(id: Long, incomingCarAdvert: IncomingCarAdvert): CarAdvert = {
    state.updateAndGet{state =>
      val nextId = if (id < state.sequence) state.sequence else id + 1
      val record = id -> incomingCarAdvert.withId(id)
      State(nextId, state.data + record)
    }.data(id)
  }

  def delete(id: Long): Option[CarAdvert] = {
    state.getAndUpdate{state =>
      State(state.sequence, state.data - id)
    }.data.get(id)
  }
}


