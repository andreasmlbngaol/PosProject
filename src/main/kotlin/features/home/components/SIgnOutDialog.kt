package features.home.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable

@Composable
fun SignOutDialog(
    onDismissRequest: () -> Unit,
    onSignOut: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Keluar?") },
        text = { Text(text = "Apakah kamu ingin keluar?") },
        confirmButton = {
            TextButton(
                onClick = onSignOut,
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
                onClick = onDismissRequest,
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