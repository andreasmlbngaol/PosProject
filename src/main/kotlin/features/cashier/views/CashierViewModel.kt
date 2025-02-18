package features.cashier.views

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.Camera
import model.BaseViewModel
import di.TestClass

class CashierViewModel(
    testClass: TestClass
): BaseViewModel(testClass) {

    init {
        super.test()
    }

    private val _code = MutableStateFlow("")
    val code = _code.asStateFlow()

    private val _camera = MutableStateFlow<Camera?>(null)

    private val _availableCameras = MutableStateFlow<List<Int>>(emptyList())
    val availableCameras = _availableCameras.asStateFlow()

    private val _cameraOpened = MutableStateFlow(false)
    val cameraOpened = _cameraOpened.asStateFlow()

    init {
        launchScope {
            _camera.value = Camera()
            _availableCameras.value = _camera.value!!.listAvailableCameras()
        }
    }

    fun setCode(code: String) {
        _code.value = code
    }

    fun startCapture(
        index: Int,
        onBarcodeDetected: (String) -> Unit
    ) {
        launchScope {
            if (_cameraOpened.value) return@launchScope else _cameraOpened.value = true
            _camera.value!!.startCapture(index, onBarcodeDetected)
        }
    }

    fun stopCapture() {
        launchScope {
            _camera.value!!.isOpen = false
            _cameraOpened.value = false
        }
    }
}