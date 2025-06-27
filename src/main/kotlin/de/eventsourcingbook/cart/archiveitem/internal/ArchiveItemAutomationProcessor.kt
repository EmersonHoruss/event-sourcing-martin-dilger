package de.eventsourcingbook.cart.archiveitem.internal

import de.eventsourcingbook.cart.cartswithproducts.CartsWithProductsReadModel
import de.eventsourcingbook.cart.cartswithproducts.CartsWithProductsReadModelQuery
import de.eventsourcingbook.cart.common.Processor
import de.eventsourcingbook.cart.domain.commands.archiveitem.ArchiveItemCommand
import de.eventsourcingbook.cart.events.PriceChangedEvent
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.axonframework.eventhandling.DisallowReplay

@Component
@DisallowReplay
class ArchiveItemAutomationProcessor : Processor {
  @Autowired lateinit var commandGateway: CommandGateway

  @Autowired lateinit var queryGateway: QueryGateway

  @EventHandler
  fun on(event: PriceChangedEvent) {
    queryGateway.query(
                    CartsWithProductsReadModelQuery(event.productId),
                    CartsWithProductsReadModel::class.java
            )
            .thenAccept {
              it.data.forEach { cart ->
                commandGateway.send<ArchiveItemCommand>(
                        ArchiveItemCommand(
                                aggregateId = cart.aggregateId,
                                productId = cart.productId
                        )
                )
              }
            }
  }
}
