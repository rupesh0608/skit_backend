package com.rdktechnologies.skit.error

data class ApiResponse(
    var error: Boolean? = null,
    var message: String? = null,
    var statusCode: Int? = null,
    var data: Any? = null
)

