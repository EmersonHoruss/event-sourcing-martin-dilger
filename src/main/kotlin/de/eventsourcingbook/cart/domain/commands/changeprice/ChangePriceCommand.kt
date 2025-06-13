package de.eventsourcingbook.cart.domain.commands.changeprice

import java.util.UUID
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ChangePriceCommand(
        @TargetAggregateIdentifier val productId: UUID,
        val newPrice: Double,
        val oldPrice: Double
)
