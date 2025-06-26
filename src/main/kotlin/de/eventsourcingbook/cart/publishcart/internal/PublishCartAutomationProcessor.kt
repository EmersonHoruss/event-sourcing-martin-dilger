package de.eventsourcingbook.cart.publishcart.internal

import de.eventsourcingbook.cart.common.Processor
import de.eventsourcingbook.cart.domain.commands.publishcart.PublishCartCommand
import de.eventsourcingbook.cart.events.CartSubmittedEvent
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.axonframework.eventhandling.DisallowReplay

@Component
@ProcessingGroup("publish_cart")
@DisallowReplay
class PublishCartAutomationProcessor : Processor {

  @Autowired lateinit var commandGateway: CommandGateway

  @EventHandler
  fun on(event: CartSubmittedEvent) {
    commandGateway.sendAndWait<Any>(
            PublishCartCommand(
                    aggregateId = event.aggregateId,
                    orderedProducts = event.orderedProducts,
                    totalPrice = event.totalPrice
            )
    )
  }
}
