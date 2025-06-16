package de.eventsourcingbook.cart.cartswithproducts.internal

import de.eventsourcingbook.cart.cartswithproducts.CartsWithProductsReadModel
import de.eventsourcingbook.cart.cartswithproducts.CartsWithProductsReadModelQuery
import java.util.UUID
import java.util.concurrent.CompletableFuture
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ReadOnlyCartsWithProductsResource(private var queryGateway: QueryGateway) {

  @CrossOrigin
  @GetMapping("/cartswithproducts/{productId}")
  fun findReadModel(
          @PathVariable("productId") productId: UUID
  ): CompletableFuture<CartsWithProductsReadModel> {
    return queryGateway.query(
            CartsWithProductsReadModelQuery(productId),
            CartsWithProductsReadModel::class.java
    )
  }
}
