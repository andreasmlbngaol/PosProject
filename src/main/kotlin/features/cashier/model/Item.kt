package features.cashier.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val code: Int,
    val name: String,
    val price: Float
)