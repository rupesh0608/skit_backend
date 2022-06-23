package com.rdktechnologies.skit.controller


import com.rdktechnologies.skit.model.dto.app.*
import com.rdktechnologies.skit.service.profile.SettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid



@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/app/settings/")
class AppSettingsController {

    @Autowired
    private lateinit var settingService: SettingService

    @PostMapping("/change_password")
    fun changePassword(@RequestBody(required=true) @Valid changePasswordDto: ChangePasswordDto):ResponseEntity<Any>{
    return settingService.changePassword(changePasswordDto)
    }
    @GetMapping("/about")
    fun about():ResponseEntity<Any>{
        return settingService.about()
    }

}