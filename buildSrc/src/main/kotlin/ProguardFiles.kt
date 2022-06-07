/**
 * Encapsulates the file name of all kinds of ProGuard files.
 *
 * @author Ritwik Jamuar
 */
object ProguardFiles {

	/**
	 * Default ProGuard File.
	 */
	val default by lazy { "proguard-rules.pro" }

	/**
	 * ProGuard File for Consumers of an Android Library.
	 */
	val consumer by lazy { "consumer-rules.pro" }

	/**
	 * ProGuard File for Android R8 Optimizations.
	 */
	val optimize by lazy { "proguard-android-optimize.txt" }

}
