package de.eventsourcingbook.cart.additem.internal

import de.eventsourcingbook.cart.common.CommandResult
import de.eventsourcingbook.cart.domain.commands.additem.AddItemCommand
import java.util.UUID
import java.util.concurrent.CompletableFuture
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class AddItemPayload(
        var aggregateId: UUID,
        var description: String,
        var image: String,
        var price: Double,
        var totalPrice: Double,
        var itemId: UUID,
        var productId: UUID
)

@RestController
class AddItemResource(private var commandGateway: CommandGateway) {
  var logger = KotlinLogging.logger {}

  @CrossOrigin
  @PostMapping("/additem/{aggregateId}")
  fun processCommand(
          @PathVariable("aggregateId") aggregateId: UUID,
          @RequestBody payload: AddItemPayload
  ): CompletableFuture<CommandResult> {
    return commandGateway.send(
            AddItemCommand(
                    aggregateId = aggregateId,
                    description = payload.description,
                    image = payload.image,
                    price = payload.price,
                    totalPrice = payload.totalPrice,
                    itemId = payload.itemId,
                    productId = payload.productId
            )
    )
  }
}
