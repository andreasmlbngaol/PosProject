package features.auth.views

import at.favre.lib.crypto.bcrypt.BCrypt
import kotlinx.coroutines.Dispatchers
import model.utils.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import model.BaseViewModel
import services.ApiClient

class AuthViewModel: BaseViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private fun String.verifyPassword(hashedPassword: String): Boolean {
        return BCrypt.verifyer().verify(this.toCharArray(), hashedPassword).verified
    }

    fun login(
        uid: String,
        pin: String,
        onFailure: () -> Unit,
        onSuccess: () -> Unit
    ) {
        launchScope {
            ApiClient.getUser(uid)?.let { user ->
                if(uid == user.uid && pin.verifyPassword(user.pin)) {
                    PreferenceManager.saveLogin(uid)
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                } else {
                    onFailure()
                }
            } ?: onFailure()
        }
    }

    suspend fun checkLogin(
        onLoginSuccess: () -> Unit
    ) {
        PreferenceManager.getUserId()?.let { onLoginSuccess() }
        delay(500L)
        _isLoading.value = false
    }
}