package features.home.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.ExitDialog
import features.home.components.HomeMainButton
import features.home.components.HomeTopBar
import features.home.components.SignOutDialog

@Composable
fun HomeScreen(
    onNavigateToAuth: () -> Unit,
    onNavigateToCashier: () -> Unit,
    onExitApplication: () -> Unit,
    viewModel: HomeViewModel = HomeViewModel()
) {
    val signOutDialogVisible by viewModel.signOutDialogVisible.collectAsState()
    val exitDialogVisible by viewModel.exitDialogVisible.collectAsState()

    Scaffold(
        topBar = {
            HomeTopBar(
                onShowSignOutDialog = {
                    viewModel.showSignOutDialog()
                },
                onShowExitDialog = {
                    viewModel.showExitDialog()
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HomeMainButton("Stok"){ }
                HomeMainButton("Kasir"){ onNavigateToCashier() }
                HomeMainButton("Laporan"){ }
            }
            if(signOutDialogVisible) {
                SignOutDialog(
                    onDismissRequest = { viewModel.dismissSignOutDialog() },
                    onSignOut = { viewModel.signOut(onNavigateToAuth) }
                )
            }

            if(exitDialogVisible) {
                ExitDialog(
                    onDismissRequest = { viewModel.dismissExitDialog() },
                    onExitApplication = onExitApplication
                )
            }
        }
    }
}