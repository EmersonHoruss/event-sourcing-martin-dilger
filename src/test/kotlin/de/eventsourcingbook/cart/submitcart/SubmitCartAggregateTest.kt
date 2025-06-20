package de.eventsourcingbook.cart.submitcart

import de.eventsourcingbook.cart.events.CartSubmittedEvent
import de.eventsourcingbook.cart.events.OrderedProduct
import de.eventsourcingbook.cart.common.Event
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.domain.CartAggregate
import de.eventsourcingbook.cart.domain.commands.submitcart.SubmitCartCommand
import de.eventsourcingbook.cart.events.CartCreatedEvent
import de.eventsourcingbook.cart.events.ItemAddedEvent
import java.util.UUID
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SubmitCartAggregateTest {

  private lateinit var fixture: FixtureConfiguration<CartAggregate>

  @BeforeEach
  fun setUp() {
    fixture = AggregateTestFixture(CartAggregate::class.java)
  }

  @Test
  fun `SubmitCartAggregateTest`() {
    // GIVEN
    val events = mutableListOf<Event>()

    val aggregateId = UUID.fromString("2519f0c5-040b-426c-9565-82634c1ede2f")
    val productId1 = UUID.randomUUID()
    val price1 = 9.99
    val productId2 = UUID.randomUUID()
    val price2 = 10.99

    events.add(RandomData.newInstance<CartCreatedEvent> { this.aggregateId = aggregateId })
    events.add(
            RandomData.newInstance<ItemAddedEvent> {
              this.aggregateId = aggregateId
              this.price = price1
              this.itemId = UUID.randomUUID()
              this.productId = productId1
            }
    )
    events.add(
            RandomData.newInstance<ItemAddedEvent> {
              this.aggregateId = aggregateId
              this.price = price2
              this.itemId = UUID.randomUUID()
              this.productId = productId2
            }
    )

    // WHEN
    val command = SubmitCartCommand(aggregateId = aggregateId)

    // THEN
    val expectedEvents = mutableListOf<Event>()

    expectedEvents.add(
            RandomData.newInstance<CartSubmittedEvent> {
              this.aggregateId = command.aggregateId
              this.orderedProducts =
                      listOf(
                              OrderedProduct(productId1, price1),
                              OrderedProduct(productId2, price2)
                      )
              this.totalPrice = price1 + price2
            }
    )

    fixture.given(events)
            .`when`(command)
            .expectSuccessfulHandlerExecution()
            .expectEvents(*expectedEvents.toTypedArray())
  }
}
