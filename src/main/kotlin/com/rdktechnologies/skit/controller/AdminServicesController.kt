package com.rdktechnologies.skit.controller


import com.rdktechnologies.skit.model.dto.app.*
import com.rdktechnologies.skit.model.dto.adminpannel.CreateServiceDto
import com.rdktechnologies.skit.model.response.app.SimpleResponse
import com.rdktechnologies.skit.service.auth.IAuthService
import com.rdktechnologies.skit.service.services.IServicesService
import com.rdktechnologies.skit.service.users.UsersService
import com.rdktechnologies.skit.utils.FileHelper
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid
import javax.validation.constraints.NotEmpty


@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/admin/services/")
class AdminServicesController {

    @Autowired
    private lateinit var authService : IAuthService
    @Autowired
    private lateinit var service: IServicesService

    @GetMapping("/all")
    fun getAllServices():ResponseEntity<Any>{

        return service.getAllServices()
    }
    @PostMapping("/create_service_module")
    fun createServiceModule(
        @RequestParam(required = true) name:String,
        @RequestParam(required = true) image:MultipartFile
    ):ResponseEntity<Any>{
        return service.createServiceModule(name,image)
    }
    @PostMapping("/create_service")
    fun createService(
        @RequestParam(required = true) moduleId:Long,
        @RequestParam(required = true) name:String,
        @RequestParam(required = true) image:MultipartFile,
        @RequestParam(required = true) link:String
    ):ResponseEntity<Any>{
        return service.createService(moduleId,name,image,link)
    }
    @PostMapping("/delete_service_module/{id}")
    fun deleteServiceModule(@PathVariable(name = "id") id:Long):ResponseEntity<Any>{
           return service.deleteServiceModule(id)
    }
    @PostMapping("/delete_service/{id}")
    fun deleteService(@PathVariable(name = "id") id:Long):ResponseEntity<Any>{
        return service.deleteService(id)
    }
//    @PostMapping("/update")
//    fun updateService(@RequestBody(required = true) list:MutableList<Long>):ResponseEntity<Any>{
//        return service.updateService()
//    }
//
    @PostMapping("/enable_disable_service_module/{id}")
    fun enableDisableServiceModule(@PathVariable(name = "id") moduleId: Long):ResponseEntity<Any>{
        return service.enableServiceModule(moduleId)
    }
    @PostMapping("/enable_disable_service/{id}")
    fun enableDisableService(@PathVariable(name = "id") serviceId: Long):ResponseEntity<Any>{
        return service.enableService(serviceId)
    }
//    @PostMapping("/upload_logo")
//    fun uploadServiceLogo(@RequestParam("logo", required = true)
//                          logo: MultipartFile,):ResponseEntity<Any>{
////        var url= "https://s-kit.herokuapp.com/api/download"
//        var url="http://localhost:8080/api"
//        url += FileHelper().createFileAndGetUrl(logo)
//        return ResponseEntity.ok(SimpleResponse(false,200,url))
//    }

//

}