package de.eventsourcingbook.cart.archiveitem

import de.eventsourcingbook.cart.common.Event
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.domain.CartAggregate
import de.eventsourcingbook.cart.domain.commands.archiveitem.ArchiveItemCommand
import de.eventsourcingbook.cart.events.*
import java.util.UUID
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArchiveItemAggregateTest {

  private lateinit var fixture: FixtureConfiguration<CartAggregate>

  @BeforeEach
  fun setUp() {
    fixture = AggregateTestFixture(CartAggregate::class.java)
  }

  @Test
  fun `ArchiveItemAggregateTest`() {
    // GIVEN
    val itemId = UUID.randomUUID()
    val events = mutableListOf<Event>()
    val productId = UUID.randomUUID()
    val aggregateId = UUID.fromString("9952be8f-15c9-4965-91bd-586c716c5a62")

    events.add(RandomData.newInstance<CartCreatedEvent> { this.aggregateId = aggregateId })
    events.add(
            RandomData.newInstance<ItemAddedEvent> {
              this.aggregateId = aggregateId
              this.description = RandomData.newInstance {}
              this.image = RandomData.newInstance {}
              this.price = RandomData.newInstance {}
              this.productId = productId
              this.itemId = itemId
            }
    )

    // WHEN
    val command = ArchiveItemCommand(aggregateId = aggregateId, productId = productId)

    // THEN
    val expectedEvents = mutableListOf<Event>()

    expectedEvents.add(
            RandomData.newInstance<ItemArchivedEvent> {
              this.aggregateId = aggregateId
              this.itemId = itemId
            }
    )

    fixture.given(events)
            .`when`(command)
            .expectSuccessfulHandlerExecution()
            .expectEvents(*expectedEvents.toTypedArray())
  }
}
