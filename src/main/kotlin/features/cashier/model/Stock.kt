package features.cashier.model

import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    val id: Int,
    val code: String,
    val name: String,
    val price: Float,
    val stock: Int
)