package com.rdktechnologies.skit.service.profile

import com.rdktechnologies.skit.model.dto.app.*
import org.springframework.http.ResponseEntity

interface ISettingService {
 fun changePassword(changePasswordDto:ChangePasswordDto):ResponseEntity<Any>
 fun about():ResponseEntity<Any>
}