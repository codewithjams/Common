# Understanding the Use Case:

In most Android Applications that we have saw, following are the common features that must exist for an Application:

 - Fetching Data from Web.
 - Storing some Data in the device.
 - Showing Pictures and Animations.
 - Show useful Dialogs, Pop-Ups and other UI Elements.

We must have used some common Open Source Libraries to achieve the above functionalities, like:

 - [Retrofit](https://square.github.io/retrofit/): To perform RESTful API Call and receive their Callbacks.
 - [Picasso](https://square.github.io/picasso/): To show the Image, stored over web and cache the downloaded Image as well for less dependence on Network Connectivity.
 - [Shimmer Layout](https://github.com/team-supercharge/ShimmerLayout): To show the Loading Animation as a Shimmer Effect.
 - [Google Material](https://github.com/material-components/material-components-android): To get access to all the UI elements available in Google Material Design.
 - [Data Store](https://developer.android.com/jetpack/androidx/releases/datastore): Android Jetpack Component to store the Persistent Data in thread-safe manner.

As we progress over development of the application, it becomes evident that writing code over a single component introduces a lot of problem, including hard to debug, hard to navigate to different portions of code, not performant at all and such.

This is where Design Pattern shines, which promotes separation of Concerns. Currently [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) is the industry accepted (and also promoted by Google) Design Pattern for Mobile Applications where responsibilities of a View is divided into three components:

 - View: Renders the UI.
 - Model: Handles the Data.
 - ViewModel: Controls the UI interaction with Data.

Now, to enforce MVVM in our application, we must have used below library:

 - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle): Android Jetpack Component that facilitates `ViewModel` and `LiveData`.

Also, to adhere to S. O. L. I. D. Principles, we must use Dependency Injection with the help below library:

 - [Dagger](https://dagger.dev/dev-guide/android.html): To Inject Dependencies across the whole application.

As you can see, these many libraries needs a set-up code for every project every time. Writing the set-up code for every project can make these code boilerplate in nature. Also, setting-up these libraries does not vary much if the use case is simple.

This is what is my attempt by making a library that contains all the boilerplate under the hood, so that we are more concerned towards Business Logic and UX behavior.
