package com.rdktechnologies.skit.model.response.app

data class EditProfileResponse (
    var error:Boolean?=null,
    var statusCode:Int?=null,
    var message:String?=null,
    var data:Profile?=null
)
data class Profile(
    var id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email:String?=null,
    var picUrl:String?=null,
    var phoneNumber:Long?=null,
    var isGoogleLogin:Boolean?=null,
)