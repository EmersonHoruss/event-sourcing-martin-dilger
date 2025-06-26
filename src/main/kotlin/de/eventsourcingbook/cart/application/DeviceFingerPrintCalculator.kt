package de.eventsourcingbook.cart.application

import java.util.UUID
import org.springframework.stereotype.Component

@Component
class DeviceFingerPrintCalculator {

  fun calculateDeviceFingerPrint(): String = UUID.randomUUID().toString()

  companion object {
    val DEFAULT_FINGERPRINT: String = "default-fingerprint"
  }
}
