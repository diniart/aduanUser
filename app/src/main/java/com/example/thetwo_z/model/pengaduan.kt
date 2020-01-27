package com.example.thetwo_z.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class pengaduan (val idPengaduan:String,val fromId: String,val toId:String,val judul:String, val text:String,  val imageAduanUrl:String, val timeStamp:Long):
    Parcelable {
    constructor() : this("", "", "","","","",-1)
}


