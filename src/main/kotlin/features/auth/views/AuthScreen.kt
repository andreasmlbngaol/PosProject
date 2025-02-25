package features.auth.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import features.auth.components.AuthTopBar
import components.ExitDialog
import features.auth.components.AuthForm
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AuthScreen(
    onExitApplication: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: AuthViewModel = koinViewModel<AuthViewModel>()
) {
    LaunchedEffect(Unit) {
        viewModel.checkLogin { onNavigateToHome() }
    }

    var closeDialogVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AuthTopBar { closeDialogVisible = true }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            AuthForm { uid, pin, onFailure ->
                viewModel.login(
                    uid = uid,
                    pin = pin,
                    onSuccess = { onNavigateToHome() },
                    onFailure = onFailure
                )
            }

            if(closeDialogVisible) {
                ExitDialog(
                    onDismissRequest = { closeDialogVisible = false },
                    onExitApplication = onExitApplication
                )
            }
        }
        if(viewModel.isLoading.collectAsState().value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}