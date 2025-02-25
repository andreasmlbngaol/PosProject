package features.auth.views

import model.utils.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.BaseViewModel

class AuthViewModel: BaseViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    suspend fun checkLogin(
        onLoginSuccess: () -> Unit
    ) {
        PreferenceManager.getUserId()?.let { onLoginSuccess() }
        delay(500L)
        _isLoading.value = false
    }
}