package de.eventsourcingbook.cart.clearcart

import de.eventsourcingbook.cart.common.Event
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.domain.CartAggregate
import de.eventsourcingbook.cart.domain.commands.clearcart.ClearCartCommand
import de.eventsourcingbook.cart.events.CartClearedEvent
import de.eventsourcingbook.cart.events.CartCreatedEvent
import de.eventsourcingbook.cart.events.ItemAddedEvent
import java.util.UUID
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearCartAggregateTest {

  private lateinit var fixture: FixtureConfiguration<CartAggregate>

  @BeforeEach
  fun setUp() {
    fixture = AggregateTestFixture(CartAggregate::class.java)
  }

  @Test
  fun `ClearCartAggregateTest`() {
    // GIVEN
    val events = mutableListOf<Event>()
    val aggregateId = UUID.fromString("f9602c89-46e3-4202-ac78-efd39339321b")

    events.add(RandomData.newInstance<CartCreatedEvent> { this.aggregateId = aggregateId })
    events.add(RandomData.newInstance<ItemAddedEvent> { this.aggregateId = aggregateId })

    // WHEN
    val command = ClearCartCommand(aggregateId)

    // THEN
    val expectedEvents = mutableListOf<Event>()

    expectedEvents.add(
        RandomData.newInstance<CartClearedEvent> { this.aggregateId = command.aggregateId })

    fixture
        .given(events)
        .`when`(command)
        .expectSuccessfulHandlerExecution()
        .expectEvents(*expectedEvents.toTypedArray())
  }
}
