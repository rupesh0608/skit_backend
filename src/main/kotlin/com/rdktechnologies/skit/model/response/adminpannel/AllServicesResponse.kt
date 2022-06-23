package com.rdktechnologies.skit.model.response.adminpannel

import com.rdktechnologies.skit.entity.ServiceModule

data class AllServicesResponse(
    var error: Boolean? = false,
    var message: String? = "",
    var data: MutableList<ServiceModule>? = mutableListOf()
)