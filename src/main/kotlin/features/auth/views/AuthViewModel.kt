package features.auth.views

import features.auth.utils.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.BaseViewModel
import model.di.TestClass

class AuthViewModel(
    testClass: TestClass
): BaseViewModel(testClass) {
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