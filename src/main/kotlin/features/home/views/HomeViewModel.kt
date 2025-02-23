package features.home.views

import model.utils.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.BaseViewModel

class HomeViewModel : BaseViewModel() {
    private val _signOutDialogVisible = MutableStateFlow(false)
    val signOutDialogVisible = _signOutDialogVisible.asStateFlow()

    private val _exitDialogVisible = MutableStateFlow(false)
    val exitDialogVisible = _exitDialogVisible.asStateFlow()

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