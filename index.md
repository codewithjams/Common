# Common Library

An Android Library containing all the set-up codes of variety of Android Components, including `ViewModel`, `Activity`, `Fragment` etc.

### Understanding the Use Case:

In most of Android Applications that we have saw, following are the common features that must exist for an Application:

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

### Contents of common Library:

 This Android Studio Project consist of two modules:

 - **app**: Phone and Tablet Module from which APK will be generated.
 - **common**: Android Library consisting of all the common libraries with their code set-up done under the hood.

[Here](acknowledgement.md) is the list of all the Open Source Libraries used under `commmon` Library.

List of classes and resources exist on **common** module:

Package Name: *sample.ritwik.common*

 - java
   - *component*: Package to contain sub-packages and classes related to components of this Application.
      - *network*: Package to contain classes related to REST API Call over Network.
        - **BaseAuthenticator**.kt: Abstract Class implementing `okhttp3.Authenticator` so that other class can extend this to provide the Authentication Logic on each response.
        - **BaseInterceptor**.kt: Abstract Class implementing `okhttp3.Interceptor` so that other class can extend this to intercept each request.
        - **CacheInterceptor**.kt: An extension of **`BaseInterceptor`** to add Cache Control on every request.
      - *persistence*: Package to contain classes related to Persistence Storage.
        - **DataStorePreference**.kt: A wrapper Class to expose common methods of Persistence Storage implemented using `androidx.datastore.core.DataStore`.
   - *data*: Package to contain sub-packages and classes that represents themselves as the Data Classes.
      - *network*:  Package to contain Data Classes related to REST API Call over Network.
        - **BaseErrorResponse**.kt: Abstract Class representing the Response Body of Error, such that other class extends it to make use of it's functionality.
        - **NetworkType**.kt: Sealed Class encapsulating different types of Network.
        - **ResultWrapper**.kt: Sealed Class encapsulating different types of REST Response.
      - *ui*: Package to contain Data Classes related to UI.
        - **ErrorData**.kt: Data Class to encapsulate the Data of Error.
        - **NetworkData**.kt: Data Class to encapsulate the Data of Network Connectivity.
        - **PopUpData**.kt: Data Class to encapsulate the Data of Pop-Up Window.
        - **ProgressData**.kt: Data Class to encapsulate the Data of Progress Loading.
   - *di*: Package to contain sub-packages and classes related to Dependency Injection implemented using Dagger2.
      - *module*: Package to contain classes annotated with `dagger.Module` that acts as Dagger Module responsible for facilitates the dependencies.
        - **CacheModule**.kt: `dagger.Module` to facilitate `okhttp3.Cache`.
        - **CommonModule**.kt: `dagger.Module` to facilitate all the dependencies of `common` Android Library, including **`NetworkUtils`,** **`ResourceUtils`** and **`ViewUtils`**.
        - **GsonModule**.kt: `dagger.Module` to facilitate `com.google.gson.Gson`.
        - **InterceptorModule**.kt: `dagger.Module` to facilitate **`CacheInterceptor`** and `okhttp3.logging.HttpLoggingInterceptor`.
        - **MoshiModule**.kt: `dagger.Module` to facilitate `Moshi` and `MoshiConverterFactory`.
        - **NetworkModule**.kt: `dagger.Module` to facilitate `okhttp3.OkHttpClient`.
        - **PersistenceModule**.kt: `dagger.Module` to facilitate **`DataStorePreference`**.
        - **PicassoModule**.kt: `dagger.Module` to facilitate `com.squareup.picasso.Picasso`.
        - **RetrofitModule**.kt: `dagger.Module` to facilitate `retrofit2.Retrofit`.
        - **ViewModelModule**.kt: `dagger.Module` to facilitate **`VMFactory`**.
      - **ApplicationContext**.kt: Annotation Class annotated with `javax.inject.Qualifier` to mark any method return type or parameter as one related with Application's `android.content.Context`.
      - **AppScope**.kt: Annotation Class annotated with `javax.inject.Scope` to mark any method return type or parameter under one Scope of injection.
   - *mvvm*: Package to contain sub-packages and classes related to MVVM Design Pattern.
     - *model*: Package to contain classes related to Model of **M**VVM.
       - **BaseModel**.kt: Abstract Class representing the Model Part of MVVM, encapsulating all the classes of package `data -> ui`.
     - *repository*: Packages to contain classes related to Repository of `ViewModel` in MV**VM**.
       - **BaseRepository**.kt: Abstract Class representing the Repository of **`BaseViewModel`**.
     - *viewModel*: Package to contain classes related to `ViewModel` of MV**VM**.
       - **BaseViewModel**.kt: Abstract Class as an extension of `androidx.lifecycle.ViewModel` to contain the core code set-up for a self sufficient `ViewModel` that has all the commonly used code and functionality encapsulated in it.
       - **ViewModelKey**.kt: Annotation Class annotated with `dagger.MapKey` to facilitating injecting of `androidx.lifecycle.ViewModel` using Dagger2.
       - **VMFactory**.kt: An extension of `androidx.lifecycle.ViewModelProvider.Factory` to mark itself as general `androidx.lifecycle.ViewModel` Provider.
   - *ui*: Package to contan classes related to UI Elements within Android Framework, such as Activity, Fragment, Dialog etc.
     - *activity*: Package to contain classes related to `android.app.Activity`.
       - **BaseActivity**.kt: Abstract Class as an extension of `androidx.appcompat.app.AppCompatActivity` to render an Activity in the UI that contains common components required for an Activity, fused with **`BaseViewModel`**.
     - *adapter*: Package to contain classes related to various kinds of Adapters.
       - **BaseSingleVHAdapter**.kt: Abstract Class as an extension of `androidx.recyclerview.widget.RecyclerView.Adapter` that supports 1 **`BaseViewHolder`**.
       - **BaseMultipleVHAdapter**.kt: Abstract Class as an extension of `androidx.recyclerview.widget.RecyclerView.Adapter` that supports multiple `androidx.recyclerview.widget.RecyclerView.ViewHolder`s.
     - *dialog*: Package to contain classes related to various kinds of Dialogs.
       - **BaseBottomSheetDialog**.kt: Abstract Class as an extension of `com.google.android.material.bottomsheet.BottomSheetDialogFragment` to render a Bottom Sheet Dialog in the UI, containing all the common code set-up.
       - **BaseDialogFragment**.kt: Abstract Class as an extension of `androidx.fragment.app.DialogFragment` to render a Dialog in the UI, containing all the common code set-up.
     - *fragment*: Package to contain classes related to different kinds of Fragments.
       - **BaseFragment**.kt: Abstract Class as an extension of `androidx.fragment.app.Fragment` to render a Fragment in the UI, contain all the common code set-up integrated to work with **`BaseActivity`**, **`BaseModel`** and **`BaseViewModel`**.
     - *miscellaneous*: Package to contain classes related to other arbitary UI Elements.
       - **PopUpWindow**.kt: Class to package code of `android.widget.PopupWindow` to take care of it's rendering in the UI.
     - *viewHolder*: Package to contain classes related to `ViewHolders` of `RecyclerView`.
       - **BaseViewHolder**.kt: Abstract Class as an extension of `androidx.recyclerview.widget.RecyclerView` which is Lifecycle Aware.
   - *utility*: Package to contain sub-packages and classes that are utilitarian in nature.
     - *constant*: Package to contain Kotlin Files designated for storing Constant Values.
       - **DaggerNamedParameters**.kt: Kotlin File to contain the Constant Strings used in conjunction with `javax.inject.Named` Annotated Parameter or Return Type of a `dagger.Provides` method for this `common` Library.
       - **ModelConstants**.kt: Kotlin File to contain the Constant Integers used in conjunction with **`BaseModel`**'s `action` to denote an action to be performed on UI.
       - **NetworkUtilsConstants**.kt: Kotlin File to contain the Constant Values related to **`NetworkUtils`** and **`NetworkType`**.
       - **PermissionUtilsConstants**.kt: Kotlin File to contain the Constant Values related to **`PermissionUtils`**.
     - *helper*: Package to contain Kotlin Files and Classes that are utilitarian in nature.
       - **CustomExceptions**.kt: Kotlin File to contain the Custom Exception Signatures.
       - **NetworkUtils**.kt: Utility Class to provide the current state of Network Connectivity.
       - **PermissionUtils**.kt: Utility Class to provide the common methods related to asking and granting Permissions from the User.
       - **ResourceUtils**.kt: Utility Class to provide the common methods that helps in fetching the assets in the given Application.
       - **ViewUtils**.kt: Utility Class to provide the common methods helps in resolving the certain values from a given View.
 - res
   - anim
     - **dialog_animation_in**.xml: Default Enter Animation of **`BaseDialogFragment`**.
     - **dialog_animation_out**.xml: Default Exit Animation of **`BaseDialogFragment`**.
     - **slide_down**.xml: Default Enter Animation of **`PopUpWindow`**.
     - **slide_up**.xml: Default Enter Animation of **`PopUpWindow`**.
   - drawable
     - **ic_baseline_chevron_left_24**.xml: Default Back Arrow for `ActionBar`.
     - **ic_baseline_close**.xml: Default Close Icon for **`PopUpWindow`**.
   - font
     - **roboto**.xml: Downloadable Font: Roboto Regular.
   - layout
     - **view_pop_up_window**.xml: Layout of **`PopUpWindow`**.
   - values
     - **colors**.xml: Contains Colors used in `common` Library.
     - **font_certs**.xml: Font Certificates of all the downloadable fonts.
     - **preloaded_fonts**.xml: Preloaded Fonts.
     - **strings**.xml: Contains Strings used in `common` Library.
     - **styles**.xml: Contains Styles uded in `common` Library.

