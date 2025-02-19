package features.home.views

import model.utils.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.BaseViewModel
import di.TestClass
import model.User
import services.ApiClient

class HomeViewModel(
    testClass: TestClass
): BaseViewModel(testClass) {
    private val _signOutDialogVisible = MutableStateFlow(false)
    val signOutDialogVisible = _signOutDialogVisible.asStateFlow()

    private val _exitDialogVisible = MutableStateFlow(false)
    val exitDialogVisible = _exitDialogVisible.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    private val _hello = MutableStateFlow("")
    val hello = _hello.asStateFlow()

    init {
        launchScope {
            _users.value = ApiClient.getAllUsers()
            _hello.value = ApiClient.getHello()
        }
    }

    fun showSignOutDialog() {
        _signOutDialogVisible.value = true
    }

    fun dismissSignOutDialog() {
        _signOutDialogVisible.value = false
    }

    fun showExitDialog() {
        _exitDialogVisible.value = true
    }

    fun dismissExitDialog() {
        _exitDialogVisible.value = false
    }

    fun signOut(
        onSuccess: () -> Unit
    ) {
        PreferenceManager.clearLogin()
        dismissSignOutDialog()
        onSuccess()
    }
}