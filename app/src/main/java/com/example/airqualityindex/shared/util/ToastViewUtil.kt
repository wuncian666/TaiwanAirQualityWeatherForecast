package com.example.airqualityindex.shared.util

import com.coder.vincent.smart_toast.SmartToast

class ToastViewUtil {

    fun getToastView(data: String) {
        return SmartToast.classic()
            .config()
            .messageSize(18f)
            .apply()
            .show(data)
    }
}