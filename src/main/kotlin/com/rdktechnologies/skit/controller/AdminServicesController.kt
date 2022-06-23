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

//    @PostMapping("/update")
//    fun updateService(@RequestBody(required = true) list:MutableList<Long>):ResponseEntity<Any>{
//        return service.updateService()
//    }
//
//    @PostMapping("/enable")
//    fun enableService(@RequestBody(required = true) list:MutableList<Long>):ResponseEntity<Any>{
//        return service.enableService()
//    }
//    @PostMapping("/upload_logo")
//    fun uploadServiceLogo(@RequestParam("logo", required = true)
//                          logo: MultipartFile,):ResponseEntity<Any>{
////        var url= "https://s-kit.herokuapp.com/api/download"
//        var url="http://localhost:8080/api"
//        url += FileHelper().createFileAndGetUrl(logo)
//        return ResponseEntity.ok(SimpleResponse(false,200,url))
//    }
//    @PostMapping("/delete/{id}")
//    fun deleteService(@PathVariable(name = "id") id:Long):ResponseEntity<Any>{
//           return service.deleteService(id)
//    }
//

}