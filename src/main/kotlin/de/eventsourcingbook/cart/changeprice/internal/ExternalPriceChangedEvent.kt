package de.eventsourcingbook.cart.changeprice.internal

import java.math.BigDecimal
import java.util.UUID

data class ExternalPriceChangedEvent(
        var productId: UUID,
        var price: BigDecimal,
        var oldPrice: BigDecimal
)
