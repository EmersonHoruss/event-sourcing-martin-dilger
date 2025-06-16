package de.eventsourcingbook.cart.cartswithproducts

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.io.Serializable
import java.util.UUID

data class CartsWithProductsReadModelQuery(val productId: UUID)

data class CartProductId(var aggregateId: UUID, var productId: UUID) : Serializable

@IdClass(CartProductId::class)
@Entity
class CartsWithProductsReadModelEntity {
        @Id @Column(name = "cart_id") lateinit var aggregateId: UUID
        @Id @Column(name = "product_id") lateinit var productId: UUID
}

data class CartsWithProductsReadModel(val data: List<CartsWithProductsReadModelEntity>)
