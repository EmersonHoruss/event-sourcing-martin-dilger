package de.eventsourcinbook.cart.events

import de.eventsourcingbook.cart.common.Event
import java.util.UUID

data class OrderedProducts(val productId: UUID, val price: Double)

data class CartSubmittedEvent(
        var aggregateId: UUID,
        var orderedProducts: List<OrderedProducts>,
        var totalPrice: Double
) : Event
