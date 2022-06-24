package com.rdktechnologies.skit.service.services


import com.rdktechnologies.skit.entity.ServiceModule
import com.rdktechnologies.skit.model.response.adminpannel.AllServicesResponse
import com.rdktechnologies.skit.model.response.app.*
import com.rdktechnologies.skit.repository.*
import com.rdktechnologies.skit.utils.FileHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class ServicesService : IServicesService {

    @Autowired
    private lateinit var serviceModuleRepository: ServiceModuleRepository

    @Autowired
    private lateinit var serviceRepository: ServiceRepository

    override fun getAllServices(): ResponseEntity<Any> {
        return ResponseEntity.ok(AllServicesResponse(false, "All Services", serviceModuleRepository.findAll()))
    }

    override fun createServiceModule(name: String, image: MultipartFile): ResponseEntity<Any> {

        return if (!image.isEmpty && image.contentType?.contains("image") == true) {
            serviceModuleRepository.save(
                ServiceModule(
                    image = "https://s-kit.herokuapp.com/api${FileHelper().createFileAndGetUrl(image)}",
                    name = name,
                )
            )
            ResponseEntity.ok(SimpleResponse(false, 200, "Service Module Successfully Created....."))
        } else
            ResponseEntity.ok(SimpleResponse(true, 201, "Image is required"))
    }

    override fun createService(moduleId: Long, name: String, image: MultipartFile, link: String): ResponseEntity<Any> {

        return if (!serviceModuleRepository.findById(moduleId).isPresent) {
            ResponseEntity.ok(SimpleResponse(true, 404, "invalid Service Module Id"))
        } else if (!image.isEmpty && image.contentType?.contains("image") == true) {
            val serviceModule = serviceModuleRepository.findById(moduleId).get()
            serviceModule.services?.add(
                com.rdktechnologies.skit.entity.Service(
                    image = "https://s-kit.herokuapp.com/api${FileHelper().createFileAndGetUrl(image)}",
                    name = name,
                    link = link
                )
            )
            serviceModuleRepository.save(serviceModule)
            ResponseEntity.ok(SimpleResponse(false, 200, "Service Successfully Created....."))
        } else
            ResponseEntity.ok(SimpleResponse(true, 201, "Image is required"))
    }

    override fun deleteServiceModule(moduleId: Long): ResponseEntity<Any> {
        val data = serviceModuleRepository.findById(moduleId)
        if (data.isPresent) {
            serviceModuleRepository.deleteById(moduleId)
            return ResponseEntity.ok(SimpleResponse(false, 200, "Service Module Deleted Successfully...."))
        }
        return ResponseEntity.ok(SimpleResponse(true, 401, "Invalid module id"))
    }

    //    override fun updateService(): ResponseEntity<Any> {
//        TODO("Not yet implemented")
//    }
//
    override fun deleteService(serviceId: Long): ResponseEntity<Any> {
        val data = serviceRepository.findById(serviceId)
        if (data.isPresent) {
            serviceRepository.deleteById(serviceId)
            return ResponseEntity.ok(SimpleResponse(false, 200, "Service Deleted Successfully...."))
        }
        return ResponseEntity.ok(SimpleResponse(true, 401, "Invalid module id"))

    }
//
//    override fun enableService(): ResponseEntity<Any> {
//        TODO("Not yet implemented")
//    }


}