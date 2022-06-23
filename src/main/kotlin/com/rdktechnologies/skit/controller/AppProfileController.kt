package com.rdktechnologies.skit.controller


import com.rdktechnologies.skit.model.dto.app.*
import com.rdktechnologies.skit.service.profile.ProfileService
import com.rdktechnologies.skit.service.profile.SettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.Optional
import javax.validation.Valid


@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/app/profile/")
class AppProfileController {

    @Autowired
    private lateinit var profileService: ProfileService

    @PostMapping("/edit_profile")
    fun editProfile(
        @RequestParam(required = true) firstName: String,
        @RequestParam(required = true) lastName: String,
        @RequestParam(required = true) userId: Long,
        @RequestParam(required = true) email: String,
        @RequestParam phoneNumber: Long,
        @RequestParam profilePic: Optional<MultipartFile>?=null,
    ): ResponseEntity<Any> {
        return profileService.editProfile(
            firstName = firstName,
            lastName = lastName,
            userId = userId,
            email = email,
            phone = phoneNumber,
            profilePic = profilePic
        )
    }

}