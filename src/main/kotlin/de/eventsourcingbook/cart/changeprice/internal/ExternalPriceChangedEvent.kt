package de.eventsourcingbook.cart.changeprice.internal

import java.math.BigDecimal
import java.util.UUID

data class ExternalPriceChangedEvent(
        val productId: UUID,
        val price: BigDecimal,
        val oldPrice: BigDecimal
)
