package de.eventsourcingbook.cart.changeprice.integration

import de.eventsourcingbook.cart.changeprice.internal.ExternalPriceChangedEvent
import de.eventsourcingbook.cart.common.support.BaseIntegrationTest
import de.eventsourcingbook.cart.common.support.StreamAssertions
import de.eventsourcingbook.cart.common.support.awaitUntilAssserted
import de.eventsourcingbook.cart.events.PriceChangedEvent
import java.math.BigDecimal
import java.util.UUID
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate

class ChangePriceProcessorTest : BaseIntegrationTest() {

  @Autowired private lateinit var kafkaTemplate: KafkaTemplate<String, ExternalPriceChangedEvent>

  @Autowired private lateinit var streamAssertions: StreamAssertions

  @Test
  fun `ChangePriceProcessorTest`() {

    val aggregateId = UUID.randomUUID()
    val newPrice = BigDecimal.valueOf(25.99)
    val oldPrice = BigDecimal.valueOf(26.99)

    awaitUntilAssserted {
      kafkaTemplate.executeInTransaction {
        it.send("price_changes", ExternalPriceChangedEvent(aggregateId, newPrice, oldPrice)).get()
      }

      streamAssertions.assertEvent(aggregateId.toString()) { it is PriceChangedEvent }
    }
  }
}
