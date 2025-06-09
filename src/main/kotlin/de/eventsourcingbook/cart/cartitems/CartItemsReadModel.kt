package de.eventsourcingbook.cart.cartitems

import de.eventsourcingbook.cart.common.Event
import de.eventsourcingbook.cart.common.Query
import de.eventsourcingbook.cart.common.ReadModel
import de.eventsourcingbook.cart.events.CartCreatedEvent
import de.eventsourcingbook.cart.events.ItemAddedEvent
import java.util.UUID

class CartItemsReadModelQuery(var aggregateId: UUID) : Query

class CartItemsReadModel : ReadModel {

  var aggregateId: UUID? = null
  var totalPrice: Double = 0.0
  var data: MutableList<CartItem> = mutableListOf()

  fun applyEvent(events: List<Event>): CartItemsReadModel {
    events.forEach {
      when (it) {
        is CartCreatedEvent -> {
          this.aggregateId = it.aggregateId
        }
        is ItemAddedEvent -> {
          this.data.add(
              CartItem(
                  itemId = it.itemId,
                  aggregateId = it.aggregateId,
                  description = it.description,
                  image = it.image,
                  price = it.price,
                  productId = it.productId))

          this.totalPrice += it.price
        }
      }
    }
    return this
  }
}

data class CartItem(
    var itemId: UUID,
    var aggregateId: UUID,
    var description: String,
    var image: String,
    var price: Double,
    var productId: UUID
)
