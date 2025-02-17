package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Tooltip(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.surface,
        fontSize = MaterialTheme.typography.caption.fontSize,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.onSurface)
            .padding(8.dp)
    )
}
