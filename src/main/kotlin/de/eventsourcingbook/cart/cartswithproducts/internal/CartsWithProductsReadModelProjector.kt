package de.eventsourcingbook.cart.cartswithproducts.internal

import de.eventsourcingbook.cart.cartswithproducts.CartProductId
import de.eventsourcingbook.cart.cartswithproducts.CartsWithProductsReadModelEntity
import de.eventsourcingbook.cart.events.*
import java.util.UUID
import org.axonframework.eventhandling.EventHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Component

interface CartsWithProductsReadModelRepository :
        JpaRepository<CartsWithProductsReadModelEntity, CartProductId> {

  fun findByProductId(productId: UUID): List<CartsWithProductsReadModelEntity>

  @Modifying fun deleteAllByAggregateId(cartId: UUID): List<CartsWithProductsReadModelEntity>
}

@Component
class CartsWithProductsReadModelProjector(var repository: CartsWithProductsReadModelRepository) {

  @EventHandler
  fun on(event: ItemAddedEvent) {
    repository.save(
            CartsWithProductsReadModelEntity().apply {
              this.aggregateId = event.aggregateId
              this.productId = event.productId
            }
    )
  }

  @EventHandler
  fun on(event: ItemRemovedEvent) {
    repository.deleteById(CartProductId(event.aggregateId, event.itemId))
  }

  @EventHandler
  fun on(event: CartClearedEvent) {
    repository.deleteAllByAggregateId(event.aggregateId)
  }

  @EventHandler
  fun on(event: ItemArchivedEvent) {
    repository.deleteById(CartProductId(event.aggregateId, event.itemId))
  }
}
