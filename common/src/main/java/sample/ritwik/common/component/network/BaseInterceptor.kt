package sample.ritwik.common.component.network

import okhttp3.Interceptor
import okhttp3.Response

import java.io.IOException

/**
 * Abstract [Interceptor] for handling common set-up.
 *
 * @author Ritwik Jamuar
 */
abstract class BaseInterceptor : Interceptor {

    /*---------------------------------- Interceptor Callbacks -----------------------------------*/

    override fun intercept(chain: Interceptor.Chain): Response = processRequest(chain)

    /*------------------------------------- Abstract Methods -------------------------------------*/

    /**
     * Tells this [BaseInterceptor] to process it's request from the [chain].
     *
     * @param chain [Interceptor.Chain] intercepted from [Interceptor].
     * @return Processed [Response].
     */
    @Throws(IOException::class)
    protected abstract fun processRequest(chain: Interceptor.Chain): Response

}
