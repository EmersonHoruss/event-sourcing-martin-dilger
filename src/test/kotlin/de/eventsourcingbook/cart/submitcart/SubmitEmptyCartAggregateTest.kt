package de.eventsourcingbook.cart.submitcart

import de.eventsourcingbook.cart.common.Event
import de.eventsourcingbook.cart.domain.CartAggregate
import de.eventsourcingbook.cart.domain.commands.submitcart.SubmitCartCommand
import java.util.UUID
import org.axonframework.modelling.command.AggregateNotFoundException
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SubmitEmptyCartAggregateTest {

  private lateinit var fixture: FixtureConfiguration<CartAggregate>

  @BeforeEach
  fun setup() {
    fixture = AggregateTestFixture(CartAggregate::class.java)
  }

  @Test
  fun `SubmitEmptyCartAggregate`() {
    // GIVEN
    val events = mutableListOf<Event>()

    // WHEN
    val command = SubmitCartCommand(aggregateId = UUID.randomUUID())

    // THEN
    fixture.given(events).`when`(command).expectException(AggregateNotFoundException::class.java)
  }
}
