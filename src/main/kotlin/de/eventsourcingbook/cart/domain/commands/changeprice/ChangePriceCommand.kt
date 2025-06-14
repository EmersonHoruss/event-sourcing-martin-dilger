package de.eventsourcingbook.cart.domain.commands.changeprice

import java.util.UUID
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ChangePriceCommand(
        @TargetAggregateIdentifier var productId: UUID,
        var newPrice: Double,
        var oldPrice: Double
)
