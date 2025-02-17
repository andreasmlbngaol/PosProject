package model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class ViewModel {
    fun launchScope(
        block: suspend CoroutineScope.() -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            block()
        }
    }
}