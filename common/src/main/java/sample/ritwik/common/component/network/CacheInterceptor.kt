package sample.ritwik.common.component.network

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

import java.util.concurrent.TimeUnit

import javax.inject.Inject

/**
 * [BaseInterceptor] to add Cache to every request so that the application can be Offline Supported.
 *
 * @author Ritwik Jamuar
 */
class CacheInterceptor @Inject constructor() : BaseInterceptor() {

    /*-------------------------------- BaseInterceptor Callbacks ---------------------------------*/

    override fun processRequest(chain: Interceptor.Chain): Response {
        // Get the request from the Chain.
        var request = chain.request()

        // Add Cache Control.
        val cacheControl: CacheControl =
            CacheControl
                .Builder()
                .maxAge(5, TimeUnit.MINUTES) // Set Maximum Age for Cache.
                .maxStale(5, TimeUnit.MINUTES) // Set Maximum Stale Age for Cache.
                .build()

        // Modify Request with Cache Control.
        request = request.newBuilder().cacheControl(cacheControl).build()

        // Proceed with the Request.
        return chain.proceed(request)
    }

}
