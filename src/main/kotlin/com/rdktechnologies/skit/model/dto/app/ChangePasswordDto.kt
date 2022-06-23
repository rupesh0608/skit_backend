package com.rdktechnologies.skit.model.dto.app

data class ChangePasswordDto(
    var userId:Long?=null,
    var oldPassword:String?=null,
    var newPassword:String?=null,
    var confirmPassword:String?=null

)
