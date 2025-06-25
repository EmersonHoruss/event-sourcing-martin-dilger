package de.eventsourcingbook.cart.common.support

import java.time.Duration
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.assertj.core.api.Assertions.assertThat
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.stereotype.Component

@Component
class KafkaAssertions(val kafkaConsumerFactory: ConsumerFactory<Any, Any>) {

  fun assertRecord(topic: String, predicate: (event: ConsumerRecord<Any, Any>) -> Boolean) {

    val consumer = kafkaConsumerFactory.createConsumer()

    consumer.subscribe(listOf(topic))
    consumer.seekToBeginning(listOf())
    val records = consumer.poll(Duration.ofSeconds(5))

    assertThat(records).anyMatch(predicate)

    consumer.close()
  }
}
