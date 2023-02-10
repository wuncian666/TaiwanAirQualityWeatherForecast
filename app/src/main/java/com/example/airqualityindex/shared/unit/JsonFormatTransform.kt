package com.example.airqualityindex.shared.unit

import com.google.gson.Gson

class JsonFormatTransform {
    fun turnJsonFormat(uuid: String): String {
        val gson = Gson()
        val hashMap = HashMap<String, Any>()
        hashMap["gatewayUUID"] = uuid

        return gson.toJson(hashMap)
    }
}