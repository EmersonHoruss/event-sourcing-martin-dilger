package de.eventsourcingbook.cart.cartitems.integration

import de.eventsourcingbook.cart.common.CommandResult
import de.eventsourcingbook.cart.common.support.BaseIntegrationTest
import de.eventsourcingbook.cart.common.support.RandomData
import de.eventsourcingbook.cart.common.support.awaitUntilAssserted
import de.eventsourcingbook.cart.domain.commands.additem.AddItemCommand
import de.eventsourcingbook.cart.cartitems.CartItemsReadModel
import de.eventsourcingbook.cart.cartitems.CartItemsReadModelQuery
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class CartItemsReadModelTest : BaseIntegrationTest() {
  @Autowired private lateinit var commandGateway: CommandGateway

  @Autowired private lateinit var queryGateway: QueryGateway

  @Test
  fun `CartItemsReadModelTest`() {
    val aggregateId = UUID.randomUUID()

    var addItemCommand = RandomData.newInstance<AddItemCommand> { this.aggregateId = aggregateId }

    commandGateway.sendAndWait<CommandResult>(addItemCommand)

    awaitUntilAssserted {
      var readModel =
              queryGateway.query(
                      CartItemsReadModelQuery(aggregateId),
                      CartItemsReadModel::class.java
              )

      assertThat(readModel.get().data).isNotEmpty
    }
  }
}
