package features.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import components.ExitButton
import components.TopBar

@Composable
fun AuthTopBar(
    onShowCloseDialog: () -> Unit
) {
    TopBar(
        title = {
            Text(
                text = "Pos Project",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = { ExitButton(onShowCloseDialog) }
    )
}