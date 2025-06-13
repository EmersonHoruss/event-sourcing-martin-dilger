package de.eventsourcingbook.cart.changeprice

import de.eventsourcingbook.cart.common.Event
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.domain.commands.changeprice.ChangePriceCommand
import de.eventsourcingbook.cart.events.PriceChangedEvent
import de.eversourcingbook.cart.domain.PricingAggregate
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ChangePriceAggregateTest {

        private lateinit var fixture: FixtureConfiguration<PricingAggregate>

        @BeforeEach
        fun setUp() {
                fixture = AggregateTestFixture(PricingAggregate::class.java)
        }

        @Test
        fun `ChangePriceAggregateTest`() {
                // GIVEN
                val events = mutableListOf<Event>()

                // WHEN
                val command =
                        ChangePriceCommand(
                                productId = RandomData.newInstance {},
                                newPrice = RandomData.newInstance {},
                                oldPrice = RandomData.newInstance {}
                        )

                // THEN
                val expectedEvents = mutableListOf<Event>()

                expectedEvents.add(
                        RandomData.newInstance<PriceChangedEvent> {
                                this.productId = command.productId
                                this.newPrice = command.newPrice
                                this.oldPrice = command.oldPrice
                        }
                )

                fixture.given(events)
                        .`when`(command)
                        .expectSuccessfulHandlerExecution()
                        .expectEvents(*expectedEvents.toTypedArray())
        }
}
