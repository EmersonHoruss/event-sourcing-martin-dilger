package de.eventsourcingbook.cart.events

import de.eventsourcingbook.cart.common.Event
import java.util.UUID

data class OrderedProduct(val productId: UUID, val price: Double)

data class CartSubmittedEvent(
        var aggregateId: UUID,
        var orderedProducts: List<OrderedProduct>,
        var totalPrice: Double
) : Event
