package sample.ritwik.common.data.ui

import android.os.Parcel
import android.os.Parcelable

/**
 * Data Class to represent the data to show Error in the UI.
 *
 * @param title [String] denoting the Title of the Error.
 * @param description [String] denoting the Description of the Error.
 * @param button1Text [String] denoting the Text for Option 1.
 * @param button2Text [String] denoting the Text for Option 2.
 * @author Ritwik Jamuar
 */
data class ErrorData(
    var title: String = "",
    var description: String = "",
    var button1Text: String = "",
    var button2Text: String = ""
) : Parcelable {

    /*------------------------------------- Companion Object -------------------------------------*/

    companion object CREATOR : Parcelable.Creator<ErrorData> {
        override fun createFromParcel(parcel: Parcel): ErrorData = ErrorData(parcel)
        override fun newArray(size: Int): Array<ErrorData?> = arrayOfNulls(size)
    }

    /*--------------------------------------- Constructors ---------------------------------------*/

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    /*------------------------------------- Object Callbacks -------------------------------------*/

    override fun toString(): String = description

    /*----------------------------------- Parcelable Callbacks -----------------------------------*/

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(button1Text)
        parcel.writeString(button2Text)
    }

    override fun describeContents(): Int = 0

}
