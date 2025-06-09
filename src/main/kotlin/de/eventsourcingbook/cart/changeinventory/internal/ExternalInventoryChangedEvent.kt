package de.eventsourcingbook.cart.changeinventory.internal

import java.util.UUID

data class ExternalInventoryChangedEvent(var productId: UUID, var inventory: Int)
