package com.example.thetwo_z.model

class chatMessage(val id:String,val text:String,  val fromId:String, val toId:String,val imageMessageUrl:String, val timeStamp:Long) {
    constructor() : this("", "", "","","",-1)
}