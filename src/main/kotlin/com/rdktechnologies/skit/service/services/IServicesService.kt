package com.rdktechnologies.skit.service.services

import com.rdktechnologies.skit.model.dto.app.*
import com.rdktechnologies.skit.model.dto.adminpannel.CreateServiceDto
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface IServicesService {
 fun getAllServices():ResponseEntity<Any>
 fun createServiceModule(name:String,image:MultipartFile):ResponseEntity<Any>
 fun createService(moduleId:Long,name:String,image:MultipartFile,link:String):ResponseEntity<Any>
}