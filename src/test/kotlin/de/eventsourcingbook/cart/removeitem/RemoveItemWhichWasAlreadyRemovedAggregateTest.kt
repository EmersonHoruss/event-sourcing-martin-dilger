package de.eventsourcingbook.cart.removeitem

import de.eventsourcingbook.cart.domain.CartAggregate
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import java.util.UUID
import de.eventsourcingbook.cart.common.Event
import de.eventsourcingbook.cart.common.Command
import de.eventsourcingbook.cart.domain.commands.removeitem.RemoveItemCommand
import de.eventsourcingbook.cart.events.ItemAddedEvent
import de.eventsourcingbook.cart.events.CartCreatedEvent
import de.eventsourcingbook.cart.events.ItemRemovedEvent
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.common.CommandException

class RemoveItemWhichWasAlreadyRemovedAggregateTest {

  private lateinit var fixture: FixtureConfiguration<CartAggregate>

  @BeforeEach
  fun `RemoveItemWhichWasAlreadyRemovedAggregateTest`() {
    // GIVEN
    val events = mutableListOf<Event>()
    val itemId = UUID.randomUUID()
    val aggregateId = UUID.fromString("4bcfd5e3-51d9-4256-bc16-4d1b71ed46b8")

    events.add(RandomData.newInstance<CartCreatedEvent>{
      this.aggregateId = aggregateId
    })
    events.add(RandomData.newInstance<ItemAddedEvent>{this.itemId = itemId})
    events.add(RandomData.newInstance<ItemRemovedEvent>{this.aggregateId = aggregateId this.itemId = itemId })

    // WHEN
    val command = RemoveItemCommand(aggregateId = aggregateId, itemId = itemId)

    // THEN
    fixture
      .given(events)
    .`when`(command)
    .expectException(CommandException::class.java)
  }
}
