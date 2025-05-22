@PostMapping("/debug/additem")
fun addItem(
        @RequestParam aggregateId: UUID,
        @RequestParam description: String,
        @RequestParam image: String,
        @RequestParam price: Double,
        @RequestParam totalPrice: Double,
        @RequestParam itemId: UUID,
        @RequestParam productId: UUID
): CompletableFuture {
  return commandGateway.send(
          AddItemCommand(aggregateId, description, image, price, totalPrice, itemId, productId)
  )
}
