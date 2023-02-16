package com.example.airqualityindex.shared.util

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.smart_dialog.DialogHandle
import com.coder.vincent.smart_dialog.SmartDialog
import com.coder.vincent.smart_dialog.loading.BOX_SIZE_LARGE

class LoadingViewUtil {
    private lateinit var appCompatActivity: AppCompatActivity
    private lateinit var loadingDialog: DialogHandle

    private var showText: String = ""

    fun getAppCompatActivity(): AppCompatActivity {
        return this.appCompatActivity
    }

    fun initLoadingView(app: AppCompatActivity) {
        this.appCompatActivity = app
        this.loadingDialog = SmartDialog.loading()
            .config {
                it.boxSize(BOX_SIZE_LARGE)
                it.message(this.showText)
            }
            .create(this.appCompatActivity)
    }

    fun getLoadingView(data: String): DialogHandle {
        this.loadingDialog = SmartDialog.loading()
            .config {
                it.boxSize(BOX_SIZE_LARGE)
                it.message(data)
            }
            .create(this.appCompatActivity)
        return this.loadingDialog
    }

    fun dismissLoadView() {
        this.loadingDialog.dismiss()
    }

    fun setCheckView(message: String) {
        SmartDialog.notification()
            .config {
                it.message(message)
                it.messageStyle(TextStyle(Color.parseColor("000000"), 16f, true))
                it.confirmBtnLabel("確定")
            }
            .create(this.appCompatActivity)
            .show()
    }
}