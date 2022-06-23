package com.rdktechnologies.skit.service.profile


import com.rdktechnologies.skit.error.exceptions.UserNotFoundException
import com.rdktechnologies.skit.model.dto.app.EditProfileDto
import com.rdktechnologies.skit.model.response.app.*
import com.rdktechnologies.skit.repository.*
import com.rdktechnologies.skit.utils.FileHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.util.*


@Service
class ProfileService : IProfileService {
    @Autowired
    private lateinit var usersRepository: UserRepository

    override fun editProfile(
        firstName: String,
        lastName: String,
        userId: Long,
        email: String,
        phone: Long?,
        profilePic: Optional<MultipartFile>?
    ): ResponseEntity<Any> {

        val data = usersRepository.findById(userId)
        if (!data.isPresent) throw UserNotFoundException("user not found.")
        val user = data.get()
        user.firstName = firstName
        user.email = email
        user.lastName = lastName
        if (phone != 7898400139) {
            user.phoneNumber = phone
        }
        if (profilePic!!.isPresent) {
            val profileData=profilePic.get()
            if (!profileData.isEmpty && profileData.contentType?.contains("image") == true) {
                user.picUrl = "https://s-kit.herokuapp.com/api${FileHelper().createFileAndGetUrl(profileData)}"
            }
        }
        usersRepository.save(user)
        return ResponseEntity.ok(
            EditProfileResponse(
                false, 200, "Profile Updated Successfully!!", Profile(
                   id= user.id,
                  firstName = user.firstName,
                    lastName = user.lastName,
                    email=user.email,
                    picUrl = user.picUrl,
                    phoneNumber = user.phoneNumber,
                    isGoogleLogin = user.isGoogleLogin
                )
            )
        )
    }


}