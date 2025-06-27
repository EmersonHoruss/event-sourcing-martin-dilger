package de.eventsourcingbook.cart.support

import org.axonframework.config.EventProcessingConfiguration
import org.axonframework.eventhandling.TrackingEventProcessor
import org.springframework.stereotype.Component

@Component
class ResetStream(val eventProcessingConfiguration: EventProcessingConfiguration) {
  fun reset(processingGroup: String) {
    this.eventProcessingConfiguration.eventProcessor<TrackingEventProcessor>(processingGroup)
            .ifPresent {
              it.shutdownAsync().thenRun {
                it.resetTokens()
                it.start()
              }
            }
  }
}
