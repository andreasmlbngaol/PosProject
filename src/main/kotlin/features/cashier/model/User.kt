package features.cashier.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val uid: String,
    val pin: String
)