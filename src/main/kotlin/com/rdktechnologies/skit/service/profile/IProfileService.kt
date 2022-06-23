package com.rdktechnologies.skit.service.profile

import com.rdktechnologies.skit.model.dto.app.*
import com.rdktechnologies.skit.model.dto.adminpannel.JobDto
import com.rdktechnologies.skit.model.dto.adminpannel.UpdateJobDto
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface IProfileService {
 fun editProfile(firstName:String,
                 lastName:String,
                 userId:Long,
                 email:String,
                 phone:Long?=7898400139,
                 profilePic: Optional<MultipartFile>?=null
 ):ResponseEntity<Any>
}