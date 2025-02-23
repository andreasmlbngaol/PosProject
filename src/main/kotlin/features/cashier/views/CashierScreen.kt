package features.cashier.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.IconOpacity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import features.cashier.components.CashierTable
import features.cashier.components.CashierTopBar
import func.formatted
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

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
    val total = cartItems.sumOf { it.totalPrice.toLong() }

    LaunchedEffect(total) {
        viewModel.setPaid("", total)
    }

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
                modifier = Modifier.weight(1f),
            ) { cartItem, newQuantity ->
                viewModel.updateQuantity(cartItem, newQuantity)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f/6)
                    .align(Alignment.End)
            ) {
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

                OutlinedTextField(
                    leadingIcon = {
                        Text("Rp")
                    },
                    value = paid,
                    onValueChange = { viewModel.setPaid(it, total) },
                    label = { Text("Jumlah dibayar") },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth(),
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Done,
//                        keyboardType = KeyboardType.Number
//                    ),
//                    keyboardActions = KeyboardActions(
//                        onDone = {
//                            viewModel.setChange(total)
//                        }
//                    )
                )

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

            Button(
                onClick = { viewModel.resetProperties() },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                Text("Reset")
            }

        }
    }
}