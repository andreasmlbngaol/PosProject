package features.cashier.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
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
                    TableCell(cartItem.item.code)
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
    var quantity by remember { mutableStateOf(cartItem.quantity.toString()) }

    if (isEditing) {
        TextField(
            value = quantity,
            onValueChange = { newValue ->
                quantity = newValue.filter { it.isDigit() } // Hanya angka
            },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    isEditing = false
                    val newQuantity = quantity.toIntOrNull() ?: cartItem.quantity
                    onQuantityChange(cartItem, newQuantity)
                }
            )
        )
    } else {
        TableCell(
            text = cartItem.quantity.toString(),
            isHeader = false,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { isEditing = true }
                    )
                }
        )
    }
}
