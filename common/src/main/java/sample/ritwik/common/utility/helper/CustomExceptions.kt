package sample.ritwik.common.utility.helper

import java.lang.Exception

/**
 * Custom [Exception] as the reminiscent of any exception occurred during performing REST API Call.
 *
 * @param code [Integer] denoting the Error Code.
 * @param description [String] denoting the description of the Error.
 * @author Ritwik Jamuar
 */
class RESTAPIException(val code: Int = 0, val description: String): Exception(description)
