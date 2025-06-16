package de.eventsourcingbook.cart.cartswithproducts.integration

import de.eventsourcingbook.cart.cartswithproducts.CartsWithProductsReadModel
import de.eventsourcingbook.cart.cartswithproducts.CartsWithProductsReadModelQuery
import de.eventsourcingbook.cart.common.CommandResult
import de.eventsourcingbook.cart.common.support.BaseIntegrationTest
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.common.support.awaitUntilAssserted
import de.eventsourcingbook.cart.domain.commands.additem.AddItemCommand
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class CartsWithProductsReadModelTest : BaseIntegrationTest() {

  @Autowired private lateinit var commandGateway: CommandGateway

  @Autowired private lateinit var queryGateway: QueryGateway

  @Test
  fun `CartsWithProductsReadModelTest`() {

    val aggregateId = UUID.randomUUID()
    val productId = UUID.randomUUID()
    val itemId = UUID.randomUUID()

    val addItemCommand =
            RandomData.newInstance<AddItemCommand> {
              this.aggregateId = aggregateId
              this.productId = productId
              this.itemId = itemId
            }

    commandGateway.sendAndWait<CommandResult>(addItemCommand)

    awaitUntilAssserted {
      var readModel =
              queryGateway.query(
                      CartsWithProductsReadModelQuery(productId),
                      CartsWithProductsReadModel::class.java
              )

      assertThat(readModel.get().data).first().matches {
        it.aggregateId == aggregateId && it.productId == productId
      }
    }
  }
}
