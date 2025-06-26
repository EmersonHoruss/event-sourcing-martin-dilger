package de.eventsourcingbook.cart.submitcart.internal

import de.eventsourcingbook.cart.common.CommandResult
import de.eventsourcingbook.cart.domain.commands.submitcart.SubmitCartCommand
import java.util.UUID
import java.util.concurrent.CompletableFuture
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class SubmitCartPayload(var aggregateId: UUID)

@RestController
class SubmitCartRessource(private var commandGateway: CommandGateway) {

  @CrossOrigin
  @PostMapping("/submitcart/{aggregateId}")
  fun processCommand(
          @PathVariable("aggregateId") aggregateId: UUID,
          @RequestBody payload: SubmitCartPayload
  ): CompletableFuture<CommandResult> {
    return commandGateway.send(SubmitCartCommand(aggregateId = payload.aggregateId))
  }
}
