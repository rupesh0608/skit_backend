package com.rdktechnologies.skit.model.dto.adminpannel

import javax.validation.constraints.NotEmpty

data class CreateServiceDto(
    @field:NotEmpty(message = "must not be null or Empty")
   var logo:String,
    @field:NotEmpty(message = "must not be null or Empty")
   var name:String,
    @field:NotEmpty(message = "must not be null or Empty")
   var link:String,
    @field:NotEmpty(message = "must not be null or Empty")
   var category:String,

)
