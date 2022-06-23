package com.rdktechnologies.skit.model.dto.app

data class EditProfileDto(
    var userId:Long?=null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phoneNumber: Long? = null
)
