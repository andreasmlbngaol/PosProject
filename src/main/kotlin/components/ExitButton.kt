package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExitButton(
    onExit: () -> Unit
) {
    TooltipArea(
        tooltip = { Tooltip("Tutup Aplikasi") }
    ) {
        IconButton(
            onClick = onExit
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Exit"
            )
        }
    }
}