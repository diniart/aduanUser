package com.example.thetwo_z.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Admin (val aid:String, val adminname: String, val profileImageUrl:String): Parcelable{
    constructor() : this("", "", "")
}




