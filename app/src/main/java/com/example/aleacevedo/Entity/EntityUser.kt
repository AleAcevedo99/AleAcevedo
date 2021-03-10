package com.example.aleacevedo.Entity

data class EntityUser(
    var email:String,
    var password:String,
    var phoneNumber:String,
    var gender:Int){
    constructor():this("", "", "", 0)
}