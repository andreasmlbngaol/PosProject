package services

import features.cashier.model.Item
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    private const val BASE_URL = "http://192.168.1.6:8080"
    private const val ITEMS_URL = "$BASE_URL/items"


    val client = HttpClient {
        install(Logging) {
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getAllItems(): List<Item> {
        return client.get(ITEMS_URL).body()
    }

    suspend fun getItemByCode(code: Int): Item? {
        return client.get("$ITEMS_URL/$code").body()
    }
}