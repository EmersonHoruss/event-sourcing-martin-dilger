package de.eventsourcingbook.cart.publishcart.integration

import de.eventsourcingbook.cart.common.support.BaseIntegrationTest
import de.eventsourcingbook.cart.common.support.KafkaAssertions
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.common.support.StreamAssertions
import de.eventsourcingbook.cart.common.support.awaitUntilAssserted
import de.eventsourcingbook.cart.domain.commands.additem.AddItemCommand
import de.eventsourcingbook.cart.domain.commands.submitcart.SubmitCartCommand
import de.eventsourcingbook.cart.events.CartPublishedEvent
import de.eventsourcingbook.cart.publishcart.internal.contract.ExternalPublishedCart
import java.util.UUID
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class PublishCartProcessorTest : BaseIntegrationTest() {

  @Autowired private lateinit var commandGateway: CommandGateway

  @Autowired private lateinit var streamAssertions: StreamAssertions

  @Autowired private lateinit var kafkaAssertions: KafkaAssertions

  @Test
  fun `PublishCartProcessorTest`() {

    val aggregateId = UUID.randomUUID()

    val addItemCommand = RandomData.newInstance<AddItemCommand> { this.aggregateId = aggregateId }
    commandGateway.sendAndWait<Any>(addItemCommand)

    val submitCartCommand =
            RandomData.newInstance<SubmitCartCommand> { this.aggregateId = aggregateId }
    commandGateway.sendAndWait<Any>(submitCartCommand)

    awaitUntilAssserted {
      streamAssertions.assertEvent(aggregateId.toString()) { it is CartPublishedEvent }
      // kafkaAssertions.assertRecord("published_carts") {
      //   it.value() is ExternalPublishedCart &&
      //           (it.value() as ExternalPublishedCart).aggregateId == aggregateId
      // }
    }
  }
}
