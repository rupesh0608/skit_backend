package com.rdktechnologies.skit.model.dto.adminpannel

import javax.validation.constraints.NotEmpty

data class JobDto (
    @field:NotEmpty(message = "must not be null or Empty")
    var postName:String,
    @field:NotEmpty(message = "must not be null or Empty")
    var boardName:String,
    @field:NotEmpty(message = "must not be null or Empty")
    var qualifications:String,
    @field:NotEmpty(message = "must not be null or Empty")
    var link:String,
    @field:NotEmpty(message = "must not be null or Empty")
    var postDate:String,
    @field:NotEmpty(message = "must not be null or Empty")
    var lastDate:String,
    @field:NotEmpty(message = "must not be null or Empty")
    var category: String,
    @field:NotEmpty(message = "must not be null or Empty")
    var status:String?="draft"
)