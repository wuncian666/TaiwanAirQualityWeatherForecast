package com.example.airqualityindex.shared.util

class RegisterVerify {

    fun isLoginIdVerify(loginId: String):Boolean {
        var isLoginIdOk = false

        if (loginId.length >= 6) {
            isLoginIdOk = true
        }
        return isLoginIdOk
    }
}