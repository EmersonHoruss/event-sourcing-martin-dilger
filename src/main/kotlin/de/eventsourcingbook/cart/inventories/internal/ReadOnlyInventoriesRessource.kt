package de.eventsourcingbook.cart.inventories.internal

import de.eventsourcingbook.cart.inventories.InventoriesReadModel
import de.eventsourcingbook.cart.inventories.InventoriesReadModelQuery
import java.util.UUID
import java.util.concurrent.CompletableFuture
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ReadOnlyInventoriesRessource(private var queryGateway: QueryGateway) {

  @CrossOrigin
  @GetMapping("/inventories/{aggregateId}")
  fun findReadModel(
          @PathVariable("aggregateId") aggregateId: UUID
  ): CompletableFuture<InventoriesReadModel> {
    return queryGateway.query(
            InventoriesReadModelQuery(aggregateId),
            InventoriesReadModel::class.java
    )
  }
}
