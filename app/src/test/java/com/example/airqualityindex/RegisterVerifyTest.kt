package com.example.airqualityindex

import com.example.airqualityindex.shared.unit.RegisterVerify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RegisterVerifyTest {
    @Test
    fun loginVerifyTrue() {
        val registerVerify = RegisterVerify()

        assertTrue(registerVerify.isLoginIdVerify("123456"))
    }

    @Test
    fun loginVerifyFalse() {
        val registerVerify = RegisterVerify()

        assertFalse(registerVerify.isLoginIdVerify("12345"))
    }
}