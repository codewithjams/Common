package sample.ritwik.common.component.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Abstract [Authenticator] for handling common set-up.
 *
 * @author Ritwik Jamuar
 */
abstract class BaseAuthenticator: Authenticator {

    /*--------------------------------- Authenticator Callbacks ----------------------------------*/

    override fun authenticate(route: Route?, response: Response): Request? =
            authenticateResponse(response)

    /*------------------------------------- Abstract Methods -------------------------------------*/

    /**
     * Tells this [BaseAuthenticator] to process it's [response] from [Authenticator].
     *
     * @param response [Response] of Network from [Authenticator].
     * @return Processed [Request].
     */
    protected abstract fun authenticateResponse(response: Response): Request?

}
