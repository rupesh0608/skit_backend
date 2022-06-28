package com.rdktechnologies.skit.model.response.adminpannel

import com.rdktechnologies.skit.entity.ServiceModule

data class VerificationListResponse(
    var error: Boolean? = false,
    var message: String? = "",
    var data: MutableList<VerificationList>? = mutableListOf()
)
data class VerificationList(
    var id:Long?=null,
    var profile:verificationUserProfile,
    var documents:Any,
    var status:String?=null,
    var message: String?=null,
)
data class verificationUserProfile(
    var id:Long?=null,
var name: String? = null,
var email: String? = null,
var picUrl: String? = null,
var phoneNumber: Long? = null,
)
