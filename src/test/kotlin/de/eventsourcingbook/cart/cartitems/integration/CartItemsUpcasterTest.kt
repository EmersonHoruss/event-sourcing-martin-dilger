package de.eventsourcingbook.cart.cartitems.integration

import de.eventsourcingbook.cart.application.DeviceFingerPrintCalculator
import de.eventsourcingbook.cart.cartitems.CartItemsReadModel
import de.eventsourcingbook.cart.cartitems.CartItemsReadModelQuery
import de.eventsourcingbook.cart.common.support.BaseIntegrationTest
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.common.support.StreamAssertions
import de.eventsourcingbook.cart.domain.CartAggregate
import de.eventsourcingbook.cart.events.CartCreatedEvent
import de.eventsourcingbook.cart.events.versioned.ItemAddedEvent
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.Repository
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class CartItemsUpcasterTest : BaseIntegrationTest() {

  @Autowired private lateinit var queryGateway: QueryGateway

  @Autowired private lateinit var streamAssertions: StreamAssertions

  @Autowired private lateinit var repository: Repository<CartAggregate>

  @Test
  fun `CartItemsUpcasterTest`() {

    val aggregateId = UUID.randomUUID()
    val unit = DefaultUnitOfWork.startAndGet(null)

    unit.execute {
      val aggregate = repository.newInstance { CartAggregate() }

      aggregate.execute {
        AggregateLifecycle.apply(
                RandomData.newInstance<CartCreatedEvent> { this.aggregateId = aggregateId }
        )
        AggregateLifecycle.apply(
                RandomData.newInstance<ItemAddedEvent> { this.aggregateId = aggregateId }
        )
      }
    }

    val readModel =
            queryGateway.query(CartItemsReadModelQuery(aggregateId), CartItemsReadModel::class.java)

    assertThat(readModel.get().data).isNotEmpty
    assertThat(readModel.get().data.first().deviceFingerPrint)
            .isEqualTo(DeviceFingerPrintCalculator.DEFAULT_FINGERPRINT)
  }
}
