package kampukter.service.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeCallback
import kampukter.service.R
import kotlinx.android.synthetic.main.bar_code_read.*


class BarCodeReadActivity : AppCompatActivity() {

    private var lastText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bar_code_read)

        btnTorchOn.setOnClickListener{zxing_barcode_scanner.setTorchOn()}
        btnTorchOff.setOnClickListener { zxing_barcode_scanner.setTorchOff() }
    }

    override fun onPause() {
        super.onPause()
        zxing_barcode_scanner.pause()
    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("blablabla", " Permission is not granted")
        } else {
            zxing_barcode_scanner.decodeContinuous(callback)
            zxing_barcode_scanner.resume()
        }
    }
    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null || result.text == lastText) {
                // Prevent duplicate scans
                Log.d("blablabla", "Scan code - " + result.text + " ------")

                return
            }

            lastText = result.text
            //zxing_barcode_scanner.setStatusText(result.text)
            val intent = Intent().putExtra(EXTRA_CODE, result.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }
    companion object {
        const val EXTRA_CODE = "EXTRA_CODE"
    }
}