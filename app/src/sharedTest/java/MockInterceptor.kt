import android.net.Uri.parse
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http.StatusLine.Companion.parse
import java.io.IOException
import java.util.logging.Level.parse

class MockAPIResponse {
    lateinit var responseString: String
    var status: Int = 0
}

class MockInterceptor : Interceptor {
    interface MockInterceptorListener {
        fun setAPIResponse(url: String): MockAPIResponse?
    }

    var listener: MockInterceptorListener? = null

    fun setInterceptorListener(listener: MockInterceptorListener) {
        this.listener = listener
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val uri = chain.request().url.toUri().toString()
        val response = listener?.setAPIResponse(uri)

        if (response?.responseString != null) {
            return chain.proceed(chain.request())
                .newBuilder()
                .code(response.status)
                .message(response.responseString)
                .body(
                    response.responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                //.addHeader("content-type, application/json")
                .build()
        }  else {
            val original = chain.request()

            val request = original.newBuilder()
                .method(original.method, original.body)
                .build()

            return chain.proceed(request)
        }
    }


}