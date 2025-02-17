package model

import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.bytedeco.opencv.global.opencv_highgui.destroyAllWindows
import org.bytedeco.opencv.global.opencv_highgui.waitKey
import org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY
import org.bytedeco.opencv.global.opencv_imgproc.cvtColor
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_videoio.VideoCapture
import java.awt.Toolkit
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte

@Suppress("UNUSED")
class Camera {
    var frame: Mat? = null

    var isOpen: Boolean = false

    var isMirrored: Boolean = false

    var cameraIndex: Int = 0

    fun setMirroredStatus(mirrored: Boolean) {
        isMirrored = mirrored
    }

    fun selectCamera(index: Int) {
        cameraIndex = index
    }

    suspend fun startCapture(
        cameraIndex: Int,
        onBarcodeDetected: (String) -> Unit
    ) {
        isOpen = true
        selectCamera(cameraIndex)
        val camera = VideoCapture(this.cameraIndex) // 0 untuk kamera utama, gunakan 1 untuk kamera sekunder jika ada
        frame = Mat()

        if (!camera.isOpened) {
            println("Gagal membuka kamera")
            return
        }

        println("Kamera berhasil dibuka!")

        withContext(Dispatchers.IO) {
            while (true) {
                if(frame == null || !isOpen) break
                camera.read(frame) // Ambil frame dari kamera
                if (frame!!.empty()) continue


                val bufferedImage = if(!isMirrored) matToBufferedImage(frame!!) else matToBufferedImage(frame!!).flip()

                val barcode = decodeBarcode(bufferedImage)

                barcode?.let {
                    onBarcodeDetected(it)
                    delay(2000L)
                }

                // Tekan tombol 'ESC' untuk keluar
                if (waitKey(30) == 27) break
            }

            camera.release() // Bebaskan kamera
            destroyAllWindows() // Tutup semua window OpenCV
        }
    }

    fun listAvailableCameras(): List<Int> {
        val availableCameras = mutableListOf<Int>()

        for (i in 0..5) { // Coba dari 0 hingga 5
            val camera = VideoCapture(i)
            if (camera.isOpened) {
                availableCameras.add(i)
                camera.release() // Jangan lupa tutup kamera setelah dicek
            }
        }

        return availableCameras

    }

    companion object {
        fun playBeepSound() {
            Toolkit.getDefaultToolkit().beep()
        }
    }
}

fun decodeBarcode(image: BufferedImage): String? {
    val hints = mapOf(
        DecodeHintType.POSSIBLE_FORMATS to listOf(
            BarcodeFormat.CODE_128, // Tambahkan format barcode yang diinginkan
            BarcodeFormat.CODE_39,
            BarcodeFormat.EAN_13,
            BarcodeFormat.EAN_8,
            BarcodeFormat.UPC_A,
            BarcodeFormat.UPC_E
        )
    )

    val source = BufferedImageLuminanceSource(image)
    val bitmap = BinaryBitmap(HybridBinarizer(source))


    try {
        val result = MultiFormatReader().apply {
            setHints(hints)
        }.decodeWithState(bitmap)
        println("Barcode decoded: ${result.barcodeFormat}")
        return result.text
    } catch (e: NotFoundException) {
        return null
    }
}


class BufferedImageLuminanceSource(image: BufferedImage) : LuminanceSource(image.width, image.height) {
    private val luminances: ByteArray

    init {
        val width = image.width
        val height = image.height

        // Konversi ke grayscale
        luminances = ByteArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                val rgb = image.getRGB(x, y)
                val r = (rgb shr 16) and 0xFF
                val g = (rgb shr 8) and 0xFF
                val b = rgb and 0xFF
                luminances[y * width + x] = (0.299 * r + 0.587 * g + 0.114 * b).toInt().toByte()
            }
        }
    }

    override fun getRow(y: Int, row: ByteArray?): ByteArray {
        val width = width
        val rowArray = row ?: ByteArray(width)
        System.arraycopy(luminances, y * width, rowArray, 0, width)
        return rowArray
    }

    override fun getMatrix(): ByteArray = luminances
}

fun matToBufferedImage(mat: Mat): BufferedImage {
    val grayMat = Mat()
    cvtColor(mat, grayMat, COLOR_BGR2GRAY) // Konversi ke grayscale

    val bufferedImage = BufferedImage(grayMat.cols(), grayMat.rows(), BufferedImage.TYPE_BYTE_GRAY)
    val data = (bufferedImage.raster.dataBuffer as DataBufferByte).data
    grayMat.data().get(data) // Salin data dari Mat ke BufferedImage

    return bufferedImage
}

fun BufferedImage.flip(): BufferedImage {
    val flipped = BufferedImage(this.width, this.height, this.type)
    val g = flipped.createGraphics()
    g.drawImage(this, 0, 0, this.width, this.height, this.width, 0, 0, this.height, null)
    g.dispose()
    return flipped
}