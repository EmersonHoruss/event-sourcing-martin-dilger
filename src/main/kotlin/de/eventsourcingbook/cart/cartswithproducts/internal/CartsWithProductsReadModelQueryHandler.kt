package de.eventsourcingbook.cart.cartswithproducts.internal

import de.eventsourcingbook.cart.cartswithproducts.CartsWithProductsReadModel
import de.eventsourcingbook.cart.cartswithproducts.CartsWithProductsReadModelQuery
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class CartsWithProductsReadModelQueryHandler(
        private val repository: CartsWithProductsReadModelRepository
) {

  @QueryHandler
  fun handleQuery(query: CartsWithProductsReadModelQuery): CartsWithProductsReadModel? {
    return CartsWithProductsReadModel(repository.findByProductId(query.productId))
  }
}
