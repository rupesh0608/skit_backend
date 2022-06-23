package com.rdktechnologies.skit.service.profile


import com.rdktechnologies.skit.error.exceptions.MatchNotFoundException
import com.rdktechnologies.skit.error.exceptions.UserNotFoundException
import com.rdktechnologies.skit.model.dto.app.ChangePasswordDto
import com.rdktechnologies.skit.model.dto.app.EditProfileDto
import com.rdktechnologies.skit.model.response.app.*
import com.rdktechnologies.skit.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class SettingService : ISettingService {
    @Autowired
    private lateinit var usersRepository:UserRepository

    override fun changePassword(changePasswordDto: ChangePasswordDto): ResponseEntity<Any> {
        val data=usersRepository.findById(changePasswordDto.userId!!)
        if(!data.isPresent) throw UserNotFoundException("User Not Found.")
        val user=data.get()
        if(changePasswordDto.confirmPassword?.equals(changePasswordDto.newPassword) == true){
            if(!BCryptPasswordEncoder().matches(changePasswordDto.oldPassword,user.password)) throw MatchNotFoundException ("old password does not match..")
            user.password=BCryptPasswordEncoder().encode(changePasswordDto.confirmPassword)
            usersRepository.save(user)
            return ResponseEntity.ok(SimpleResponse(false,200,"Password Successfully changed!!"))
        }else{
            throw MatchNotFoundException("new password and confirm password must be same..")
        }
    }

    override fun about(): ResponseEntity<Any> {
        val string="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
        return ResponseEntity.ok(SimpleResponse(false,200, data =string))
    }


}