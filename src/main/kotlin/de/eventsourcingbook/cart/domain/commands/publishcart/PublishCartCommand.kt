package de.eventsourcingbook.cart.domain.commands.publishcart

import de.eventsourcingbook.cart.common.Command
import de.eventsourcingbook.cart.events.OrderedProduct
import java.util.UUID
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class PublishCartCommand(
        @TargetAggregateIdentifier override var aggregateId: UUID,
        var orderedProducts: List<OrderedProduct>,
        var totalPrice: Double
) : Command
