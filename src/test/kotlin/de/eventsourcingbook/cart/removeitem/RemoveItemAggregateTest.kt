package de.eventsourcingbook.cart.removeitem

import de.eventsourcingbook.cart.common.Event
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.domain.CartAggregate
import de.eventsourcingbook.cart.domain.commands.removeitem.RemoveItemCommand
import de.eventsourcingbook.cart.events.CartCreatedEvent
import de.eventsourcingbook.cart.events.ItemAddedEvent
import de.eventsourcingbook.cart.events.ItemRemovedEvent
import java.util.UUID
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoveItemAggregateTest {

  private lateinit var fixture: FixtureConfiguration<CartAggregate>

  @BeforeEach
  fun setUp() {
    fixture = AggregateTestFixture(CartAggregate::class.java)
  }

  @Test
  fun `RemoveItemAggregateTest`() {
    // GIVEN
    val events = mutableListOf<Event>()
    val itemId = UUID.randomUUID()
    val aggregateId = UUID.fromString("4bcfd5e3-51d9-4256-bc16-4d1b71ed46b8")

    events.add(RandomData.newInstance<CartCreatedEvent> { this.aggregateId = aggregateId })
    events.add(
        RandomData.newInstance<ItemAddedEvent> {
          this.aggregateId = aggregateId
          this.itemId = itemId
        })

    // WHEN
    val command = RemoveItemCommand(aggregateId = aggregateId, itemId = itemId)

    // THEN
    val expectedEvents = mutableListOf<Event>()

    expectedEvents.add(
        RandomData.newInstance<ItemRemovedEvent> {
          this.aggregateId = aggregateId
          this.itemId = command.itemId
        })

    fixture
        .given(events)
        .`when`(command)
        .expectSuccessfulHandlerExecution()
        .expectEvents(*expectedEvents.toTypedArray())
  }
}
