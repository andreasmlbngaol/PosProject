package ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val Shapes
    @Composable
    get() = MaterialTheme.shapes.copy(
        large = RoundedCornerShape(16.dp),
        medium = RoundedCornerShape(12.dp),
        small = RoundedCornerShape(8.dp)
    )