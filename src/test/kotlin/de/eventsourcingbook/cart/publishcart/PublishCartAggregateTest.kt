package de.eventsourcingbook.cart.publishcart

import de.eventsourcingbook.cart.common.Event
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.domain.CartAggregate
import de.eventsourcingbook.cart.domain.commands.publishcart.PublishCartCommand
import de.eventsourcingbook.cart.events.CartCreatedEvent
import de.eventsourcingbook.cart.events.CartPublishedEvent
import de.eventsourcingbook.cart.events.CartSubmittedEvent
import de.eventsourcingbook.cart.events.ItemAddedEvent
import de.eventsourcingbook.cart.publishcart.internal.PublishCartCommandHandler
import de.eventsourcingbook.cart.publishcart.internal.contract.ExternalPublishedCart
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import java.util.UUID
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.kafka.core.KafkaTemplate

@ExtendWith(MockKExtension::class)
class PublishCartAggregateTest {

  private lateinit var fixture: FixtureConfiguration<CartAggregate>

  @RelaxedMockK private lateinit var kafkaTemplate: KafkaTemplate<String, ExternalPublishedCart>

  @BeforeEach
  fun setUp() {
    fixture = AggregateTestFixture(CartAggregate::class.java)
    fixture.registerAnnotatedCommandHandler(
            PublishCartCommandHandler(kafkaTemplate, fixture.repository)
    )
  }

  @Test
  fun `PublishCartAggregateTest`() {
    // GIVEN
    val events = mutableListOf<Event>()

    val aggregateId = UUID.randomUUID()

    events.add(RandomData.newInstance<CartCreatedEvent> { this.aggregateId = aggregateId })
    events.add(
            RandomData.newInstance<ItemAddedEvent> {
              this.aggregateId = aggregateId
              this.itemId = UUID.randomUUID()
              this.productId = UUID.randomUUID()
            }
    )
    events.add(RandomData.newInstance<CartSubmittedEvent> { this.aggregateId = aggregateId })

    // WHEN
    val command =
            PublishCartCommand(
                    aggregateId = aggregateId,
                    orderedProducts = RandomData.newInstance {},
                    totalPrice = RandomData.newInstance {}
            )

    // THEN
    val expectedEvents = mutableListOf<Event>()
    expectedEvents.add(
            RandomData.newInstance<CartPublishedEvent> { this.aggregateId = aggregateId }
    )

    fixture.given(events)
            .`when`(command)
            .expectSuccessfulHandlerExecution()
            .expectEvents(*expectedEvents.toTypedArray())
  }
}
