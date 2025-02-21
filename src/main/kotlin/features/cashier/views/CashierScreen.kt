package features.cashier.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.IconOpacity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import features.cashier.components.CashierTable
import model.utils.PreferenceManager
import features.cashier.components.CashierTopBar
import func.formatted
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import kotlin.math.sin

@OptIn(KoinExperimentalAPI::class)
@Composable
fun CashierScreen(
    onBack: () -> Unit,
    viewModel: CashierViewModel = koinViewModel<CashierViewModel>()
) {
    val code by viewModel.code.collectAsState()
    val name by viewModel.name.collectAsState()
    val price by viewModel.price.collectAsState()
    val paid by viewModel.paid.collectAsState()
    val change by viewModel.change.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            CashierTopBar(onBack = onBack)
        }
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = code,
                onValueChange = viewModel::setCode,
                label = { Text("Kode") },
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(0.5f),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchItem()
                    }
                )
            )
            Divider(thickness = 2.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {},
                    label = { Text("Nama") },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.weight(1f),
                    enabled = false,
                    readOnly = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                        disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                    )
                )

                Spacer(Modifier.width(16.dp))

                OutlinedTextField(
                    leadingIcon = {
                        Text("Rp")
                    },
                    value = if (price <= 0f) "" else price.formatted(),
                    onValueChange = {},
                    label = { Text("Harga") },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.weight(1f),
                    enabled = false,
                    readOnly = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                        disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                        disabledLeadingIconColor = MaterialTheme.colors.onSurface.copy(IconOpacity)
                    )
                )
            }
            Divider(thickness = 2.dp)

            CashierTable(
                cartItems = cartItems,
                modifier = Modifier.height(300.dp)
            ) { cartItem, newQuantity ->
                viewModel.updateQuantity(cartItem, newQuantity)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f/6)
                    .align(Alignment.End)
            ) {
                val total = cartItems.sumOf { it.totalPrice.toLong() }
                OutlinedTextField(
                    leadingIcon = {
                        Text("Rp")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    value = if (cartItems.isEmpty()) "0" else total.formatted(),
                    onValueChange = {},
                    label = { Text("Total Belanja") },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                    enabled = false,
                    readOnly = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                        disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                        disabledLeadingIconColor = MaterialTheme.colors.onSurface.copy(IconOpacity)
                    )
                )

                val isError by viewModel.isPaidError.collectAsState()
                OutlinedTextField(
                    isError = isError,
                    leadingIcon = {
                        Text("Rp")
                    },
                    value = paid,
                    onValueChange = viewModel::setPaid,
                    label = { Text("Jumlah dibayar") },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.setChange(total)
                        }
                    )
                )
                AnimatedVisibility(isError) {
                    Text(
                        text = "Masukkan nilai yang valid",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.error
                    )
                }

                OutlinedTextField(
                    leadingIcon = {
                        Text("Rp")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    value = change.formatted() ,
                    onValueChange = {},
                    label = { Text("Kembalian") },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                    enabled = false,
                    readOnly = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                        disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                        disabledLeadingIconColor = MaterialTheme.colors.onSurface.copy(IconOpacity)
                    )
                )

            }
        }
    }
}