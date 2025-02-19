package services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import model.User

object ApiClient {
    const val BASE_URL = "http://192.168.1.9:8080"
    const val USERS_URL = "$BASE_URL/users"


    val client = HttpClient {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getAllUsers(): List<User> {
        return client.get(USERS_URL).body()
    }

    suspend fun getHello(): String {
        return client.get(BASE_URL).body()
    }
}