package features.cashier.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import features.cashier.model.CartItem
import func.formatted


@Composable
fun CashierTable(
    cartItems: List<CartItem>,
    modifier: Modifier = Modifier,
    onQuantityChange: (cartItem: CartItem, newQuantity: Int) -> Unit,
) {
    val headers = listOf(
        "No",
        "Kode",
        "Nama",
        "Harga Satuan",
        "Jumlah",
        "Total"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .border(1.dp, MaterialTheme.colors.secondary, MaterialTheme.shapes.small)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondary)
        ) {
            headers.forEach { header ->
                TableCell(header, true)
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondary.copy(alpha = 0.1f))
        ) {
            itemsIndexed(cartItems) { index, cartItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TableCell("${index + 1}")
                    TableCell(cartItem.item.code.toString())
                    TableCell(cartItem.item.name)
                    TableCell(cartItem.item.price.formatted())
                    QuantityCell(cartItem, onQuantityChange)
                    TableCell(cartItem.totalPrice.formatted())
                }

                Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    isHeader: Boolean = false,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = if(isHeader) 16.sp else 14.sp,
        color = if (isHeader) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onSurface,
        modifier = modifier
            .weight(1f)
            .padding(8.dp)
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun RowScope.QuantityCell(
    cartItem: CartItem,
    onQuantityChange: (CartItem, Int) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    val placeholder by remember(cartItem.quantity) { mutableStateOf(cartItem.quantity.toString()) }
    var quantity by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    fun onFinishEditQuantity() {
        isEditing = false
        val newQuantity = quantity.toIntOrNull() ?: cartItem.quantity
        onQuantityChange(cartItem, newQuantity)
        quantity = ""
    }

    if (isEditing) {
        OutlinedTextField(
            value = quantity,
            placeholder = { Text(placeholder) },
            onValueChange = { newValue ->
                quantity = newValue.filter { it.isDigit() } // Hanya angka
            },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .focusRequester(focusRequester)
                .onPreviewKeyEvent { event ->
                    if(event.type == KeyEventType.KeyDown && event.key == Key.Escape) {
                        onFinishEditQuantity()
                        true
                    } else false
                },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onFinishEditQuantity() }
            )
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

    } else {
        TableCell(
            text = cartItem.quantity.toString(),
            isHeader = false,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { isEditing = true }
                    )
                }
        )
    }
}
