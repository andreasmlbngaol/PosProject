package features.cashier.model

data class CartItem(
    val item: Stock,
    val quantity: Int
) {
    val totalPrice: Float
        get() = item.price * quantity
}