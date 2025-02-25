package services

import features.cashier.model.Stock
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    private const val BASE_URL = "http://192.168.1.6:8080"
    private const val STOCKS_URL = "$BASE_URL/stocks"


    val client = HttpClient {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getAllItems(): List<Stock> {
        return client.get(STOCKS_URL).body()
    }

    suspend fun getItemById(id: Int): Stock? {
        return client.get(STOCKS_URL) {
            url {
                parameters.append("id", id.toString())
            }
        }.body()
    }

    suspend fun getItemByCode(code: String): Stock? {
        return client.get(STOCKS_URL) {
            url {
                parameters.append("code", code)
            }
        }.body()
    }
}