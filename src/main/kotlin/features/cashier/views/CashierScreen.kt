package features.cashier.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import features.cashier.components.CashierTopBar

@Composable
fun CashierScreen(
    onBack: () -> Unit,
    @Suppress("UNUSED_PARAMETER")
    viewModel: CashierViewModel = CashierViewModel()
) {
    Scaffold(
        topBar = { CashierTopBar(onBack) }
    ) { contentPadding ->
        Text(
            text = "Cashier Screen",
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            textAlign = TextAlign.Center
        )
    }
}