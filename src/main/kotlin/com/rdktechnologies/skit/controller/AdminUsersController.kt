package com.rdktechnologies.skit.controller


import com.rdktechnologies.skit.model.dto.app.*
import com.rdktechnologies.skit.service.auth.IAuthService
import com.rdktechnologies.skit.service.users.UsersService
import com.rdktechnologies.skit.service.verification.VerificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid



@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/admin/users/")
class AdminUsersController {

    @Autowired
    private lateinit var authService : IAuthService
    @Autowired
    private lateinit var usersService: UsersService
    @Autowired
    private lateinit var verificationService: VerificationService

    @GetMapping("/all")
    fun getAllUsers():ResponseEntity<Any>{

        return usersService.getAllUsers()
    }
    @PostMapping("/block")
    fun blockUsers(@RequestBody(required = true) list:MutableList<Long>):ResponseEntity<Any>{
        return usersService.blockUsers(list)
    }
    @PostMapping("/unblock")
    fun unblockUsers(@RequestBody(required = true) list:MutableList<Long>):ResponseEntity<Any>{
        return usersService.unblockUsers(list)
    }
    @PostMapping("/delete")
    fun deleteUsers(@RequestBody(required = true) list:MutableList<Long>):ResponseEntity<Any>{
        return usersService.deleteUsers(list)
    }
    @GetMapping("/all_verification_list")
    fun getAllVerifications():ResponseEntity<Any>{
        return verificationService.getVerificationList()
    }

}