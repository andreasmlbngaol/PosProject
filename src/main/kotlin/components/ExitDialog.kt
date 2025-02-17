package components

import androidx.compose.material.*
import androidx.compose.runtime.Composable

@Composable
fun ExitDialog(
    onDismissRequest: () -> Unit,
    onExitApplication: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Tutup Aplikasi?") },
        text = { Text(text = "Apakah kamu ingin menutup aplikasi?") },
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