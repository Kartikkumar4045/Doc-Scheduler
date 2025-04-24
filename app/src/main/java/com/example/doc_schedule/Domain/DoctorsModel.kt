package com.example.doc_schedule.Domain

import android.os.Parcel
import android.os.Parcelable

data class DoctorsModel(
    val Address: String = "",
    val Biography: String = "",
    val Id: Int = 0,
    val Name: String = "",
    val Picture: String = "",
    val Special: String = "",
    val Expriense: Int = 0,
    val Location: String = "",
    val Mobile: String = "",
    val Patiens: String = "",
    val Rating: Double = 0.0,
    val Site: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Address)
        parcel.writeString(Biography)
        parcel.writeInt(Id)
        parcel.writeString(Name)
        parcel.writeString(Picture)
        parcel.writeString(Special)
        parcel.writeInt(Expriense)
        parcel.writeString(Location)
        parcel.writeString(Mobile)
        parcel.writeString(Patiens)
        parcel.writeDouble(Rating)
        parcel.writeString(Site)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<DoctorsModel> {
        override fun createFromParcel(parcel: Parcel): DoctorsModel {
            return DoctorsModel(parcel)
        }

        override fun newArray(size: Int): Array<DoctorsModel?> {
            return arrayOfNulls(size)
        }
    }

    // Compare doctors by Id only
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DoctorsModel) return false
        return Id == other.Id
    }

    override fun hashCode(): Int {
        return Id.hashCode()
    }
}
