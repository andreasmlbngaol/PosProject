package features.home.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onExitApplication: () -> Unit,
    onNavigateToCashier: () -> Unit
) {
    var closeDialogVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "POS Project",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { closeDialogVisible = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Exit"
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onNavigateToCashier,
                    modifier = Modifier.size(200.dp)
                ) {
                    Text(text = "Kasir")
                }

                repeat(2) {
                    Button(
                        onClick = {},
                        modifier = Modifier.size(200.dp)
                    ) {
                        Text(text = "Tombol $it")
                    }
                }
            }
            if(closeDialogVisible) {
                AlertDialog(
                    onDismissRequest = { closeDialogVisible = false },
                    title = { Text(text = "Keluar?") },
                    text = { Text(text = "Apakah kamu ingin keluar dari aplikasi?") },
                    confirmButton = {
                        TextButton(
                            onClick = onExitApplication,
                            colors = ButtonDefaults.textButtonColors(
                                backgroundColor = MaterialTheme.colors.error,
                                contentColor = MaterialTheme.colors.onError
                            )
                        ) {
                            Text(text = "Ya")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { closeDialogVisible = false },
                            colors = ButtonDefaults.textButtonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = MaterialTheme.colors.onPrimary
                            )
                        ) {
                            Text("Tidak")
                        }
                    }
                )
            }
        }
    }
}