data class CartCreatedEvent(var aggregateId: UUID): Event

data class ItemAddedEvent(
  var aggregateId: UUID,
  var description: String,
  var image: String,
  var price: Double,
  var itemId: UUID,
  var productId: UUID
) : Event

data class AddItemCommand(
  @TargetAggregateIdentifier override var aggregateId:UUID,
  var description:String,
  var image:String,
  var price:Double
  var totalPrice:Double,
  var itemId:UUID,
  var productId: UUID
):Command