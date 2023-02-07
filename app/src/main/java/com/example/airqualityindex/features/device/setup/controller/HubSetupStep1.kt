package com.example.airqualityindex.features.device.setup.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentHubSetupStep1Binding
import com.example.airqualityindex.features.device.setup.viewmodels.HubViewModel
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanCustomCode
import io.github.g00fy2.quickie.config.BarcodeFormat
import io.github.g00fy2.quickie.config.ScannerConfig
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get
import java.util.*

class HubSetupStep1 : Fragment() {
    private val TAG = HubSetupStep1::class.java.simpleName

    private lateinit var binding: FragmentHubSetupStep1Binding

    private val viewModel: HubViewModel = get()

    private val mQRCodeScanner = registerForActivityResult(ScanCustomCode()) {
        val uuid = getQRCodeResult(it)
        this.requestCertificationApi(uuid)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentHubSetupStep1Binding.inflate(inflater, container, false)

        this.binding.hubLayout = this

        return this.binding.root
    }

    fun onButtonClick(view: View) {
        when (view.id) {
            R.id.btn_next -> {
                this.setupQRCodeScanner()
            }
        }
    }

    private fun setupQRCodeScanner() {
        val barcodeFormat = BarcodeFormat.FORMAT_ALL_FORMATS
        this.mQRCodeScanner.launch(
            ScannerConfig.build {
                setBarcodeFormats(listOf(barcodeFormat))
                setOverlayStringRes(0)
                setOverlayDrawableRes(null)
                setHapticSuccessFeedback(false)
                setShowTorchToggle(true)
                setHorizontalFrameRatio(1.0f)
                setUseFrontCamera(false)
            }
        )
    }

    private fun getQRCodeResult(result: QRResult): String {
        val workResult = when (result) {
            is QRResult.QRSuccess -> result.content.rawValue
            QRResult.QRUserCanceled -> "user canceled"
            QRResult.QRMissingPermission -> "miss permission"
            is QRResult.QRError -> "${result.exception.javaClass.simpleName}: ${result.exception.localizedMessage}"
        }
        return workResult
    }

    private fun requestCertificationApi(uuid: String) {
        this.viewModel.requestCertificationApi(uuid.uppercase(Locale.getDefault()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                navigateByNetworkStatus()
                // todo set gateway uuid
            }
            .subscribe()
    }

    private fun navigateByNetworkStatus() {
        if (viewModel.findVisionHubSSID()) {
            findNavController().navigate(R.id.action_hubSetUpStep1_to_hubSetupStep3)
        } else {
            findNavController().navigate(R.id.action_hubSetUpStep1_to_hubSetupStep2)
        }
    }
}