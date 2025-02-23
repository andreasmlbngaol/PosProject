package features.cashier.views

import features.cashier.model.CartItem
import features.cashier.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.BaseViewModel
import services.ApiClient

class CashierViewModel : BaseViewModel() {

    private val _code = MutableStateFlow("")
    val code = _code.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _price = MutableStateFlow(0f)
    val price = _price.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    private val _paid = MutableStateFlow("")
    val paid = _paid.asStateFlow()

    private val _change = MutableStateFlow(0L)
    val change = _change.asStateFlow()

    fun resetProperties() {
        _code.value = ""
        _name.value = ""
        _price.value = 0f
        _cartItems.value = emptyList()
        _paid.value = ""
        _change.value = 0L
    }

    fun setCode(code: String) {
        _code.value = code
    }

    private fun setChange(total: Long) {
        try {
            _change.value = _paid.value.toLong() - total
        } catch (e: NumberFormatException) {
            _change.value = 0L
        }
    }

    fun setPaid(stringPaid: String, total: Long) {
        launchScope {
            _paid.value = stringPaid.filter { it.isDigit() }
            setChange(total)
        }
    }

    fun searchItem() {
        launchScope {
            ApiClient.getItemByCode(_code.value.toInt())?.let { item ->
                _name.value = item.name
                _price.value = item.price
                addItemToCart(item)
            } ?: run {
                _name.value = ""
                _price.value = 0.0f
            }
        }
    }

    private fun addItemToCart(newItem: Item) {
        val currentList = _cartItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.item.code == newItem.code }

        if (index != -1) {
            val item = currentList[index]
            currentList[index] = item.copy(quantity = item.quantity + 1)
        } else {
            currentList.add(CartItem(newItem, 1))
        }

        _cartItems.value = currentList // Perubahan state dipicu di sini
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        val currentList = _cartItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.item.code == cartItem.item.code }

        if (index != -1) {
            currentList[index] = currentList[index].copy(quantity = newQuantity)
            _cartItems.value = currentList
        }
    }
}