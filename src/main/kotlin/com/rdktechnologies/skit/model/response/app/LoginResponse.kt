package com.rdktechnologies.skit.model.response.app

import com.rdktechnologies.skit.entity.Role
import com.rdktechnologies.skit.entity.User


data class LoginResponse(
        var error: Boolean? = null,
        var statusCode: Int? = null,
        var message: String? = null,
        var token: String? = null,
        var data: LoginUserData? = null
)
data class AdminLoginResponse(
        var error: Boolean? = null,
        var statusCode: Int? = null,
        var message: String? = null,
        var token: String? = null,
        var role:Role?=null,
        var data: User? = null
)

data class LoginUserData(
        var id: Long? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var email:String?=null,
        var picUrl:String?=null,
        var phoneNumber:Long?=null,
        var isGoogleLogin:Boolean?=null,
        var verification:VerificationData?=null

)

data class VerificationData(
        var count:Int?=0,
        var status:String="none"
)