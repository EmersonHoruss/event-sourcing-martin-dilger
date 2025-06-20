package de.eventsourcingbook.cart.submitcart

import de.eventsourcingbook.cart.events.CartSubmittedEvent
import de.eventsourcingbook.cart.common.CommandException
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

class SubmitCartTwiceAggregateTest {

  private lateinit var fixture: FixtureConfiguration<CartAggregate>

  @BeforeEach
  fun setUp() {
    fixture = AggregateTestFixture(CartAggregate::class.java)
  }

  @Test
  fun `SubmitCartTwiceAggregateTest`() {
    // GIVEN
    val events = mutableListOf<Event>()

    val aggregateId = UUID.randomUUID()
    events.add(RandomData.newInstance<CartCreatedEvent> { this.aggregateId = aggregateId })
    events.add(
            RandomData.newInstance<ItemAddedEvent> {
              this.aggregateId = aggregateId
              this.price = RandomData.newInstance<Double> {}
              this.itemId = UUID.randomUUID()
              this.productId = UUID.randomUUID()
            }
    )
    events.add(RandomData.newInstance<CartSubmittedEvent> { this.aggregateId = aggregateId })

    // WHEN
    val command = SubmitCartCommand(aggregateId = aggregateId)
    fixture.given(events).`when`(command).expectException(CommandException::class.java)
  }
}
