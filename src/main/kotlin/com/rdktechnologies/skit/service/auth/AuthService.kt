package com.rdktechnologies.skit.service.auth


import com.ongraph.daverick.recipie.social.app.constants.Permissions
import com.ongraph.daverick.recipie.social.app.constants.Roles
import com.rdktechnologies.skit.entity.*
import com.rdktechnologies.skit.error.exceptions.*
import com.rdktechnologies.skit.model.dto.app.*
import com.rdktechnologies.skit.model.response.app.*
import com.rdktechnologies.skit.repository.*
import com.rdktechnologies.skit.service.AuthUserDetails
import com.rdktechnologies.skit.service.UserService
import com.rdktechnologies.skit.utils.JWTUtility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService : IAuthService {
    @Autowired
    lateinit var jwtUtility: JWTUtility

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userRepository: UserRepository


    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var privilegeRepository: PrivilegeRepository


    override fun signup(signupDto: SignupDto, type: String): ResponseEntity<Any> {
        if (userRepository.findByEmail(signupDto.email).isPresent) throw AlreadyExistException("The given email already exists.")

        val password = encodePassword(signupDto.password, signupDto.confirm_password)

        val privilege = createPrivilegeIfNotFound(name = Permissions.READ_PRIVILEGE)
        lateinit var roles: Role
        if (type == "user_signup") {
            roles = createRoleIfNotFound(name = Roles.ROLE_USER, privileges = privilege)
        }
        if (type == "admin_signup") {
            roles = createRoleIfNotFound(name = Roles.ROLE_ADMIN, privileges = privilege)
        }

        val userObj = User(
            firstName = signupDto.first_name,
            lastName = signupDto.last_name,
            email = signupDto.email,
            password = password,
            isEnabled = true,
            isAccountNonExpired = true,
            isCredentialsNonExpired = true,
            isAccountNonLocked = true, roles = roles
        )
        userRepository.save(userObj)
        return ResponseEntity.ok(SimpleResponse(false, 200, "Account created Successfully."))
    }

    override fun login(loginDto: LoginDto, type: String): ResponseEntity<Any> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDto.email,
                loginDto.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val userDetails = authentication.principal as AuthUserDetails
        if (!authentication.isAuthenticated) throw UserNotFoundException("Invalid Credentials")
        val token = jwtUtility.generateToken(userDetails)
        val user = userDetails.users
        val roles = userDetails.users.roles
        var verificationCount = 0
        var verificationStatus = "none"
        val verifiedVerifications = user.verification!!.filter { it.status == "Verified" }

        val rejectedVerifications = user.verification!!.filter { it.status == "rejected" }
        val pendingVerifications = user.verification!!.filter { it.status == "pending" }
        verificationCount=verifiedVerifications.size+pendingVerifications.size
        if (verifiedVerifications.isNotEmpty()) {
            if (pendingVerifications.isNotEmpty()) {
                verificationStatus = "pending"
            } else {
                verificationStatus = "verified"
            }
        }else{
            if (pendingVerifications.isNotEmpty()) {
                verificationStatus = "pending"
            }
        }
        if (type == "admin_login" && roles?.name == Roles.ROLE_ADMIN) {
            return ResponseEntity.ok(AdminLoginResponse(false, 200, "LoggedIn Successfully!!", token, roles, user))
        } else if (type == "user_login") {
            return ResponseEntity.ok(
                LoginResponse(
                    false, 200, "LoggedIn Successfully!!", token, LoginUserData(
                        id = user.id,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        email = user.email,
                        picUrl = user.picUrl,
                        phoneNumber = user.phoneNumber,
                        isGoogleLogin = user.isGoogleLogin,
                        verification = VerificationData(
                            count = verificationCount,
                            status = verificationStatus
                        ),
                    )
                )
            )
        } else {
            throw UserNotFoundException("Invalid Credentials")
        }
    }

    override fun resetPassword(resetPasswordDto: ResetPasswordDto): ResponseEntity<Any> {
        return if (userRepository.findByEmail(resetPasswordDto.email).isPresent) {

            val userDetails = userRepository.findByEmail(resetPasswordDto.email).get()

            userDetails.password = encodePassword(resetPasswordDto.new_password, resetPasswordDto.confirm_password)
            userRepository.save(userDetails)
            ResponseEntity.ok(SimpleResponse(false, 200, "Password changed successfully..."))
        } else {
            throw UserNotFoundException("${resetPasswordDto.email} is not Registered...")
        }
    }

    override fun forgotPassword(forgotPasswordDto: ForgotPasswordDto): ResponseEntity<Any> {
        return if (userRepository.findByEmail(forgotPasswordDto.email).isPresent) {

            ResponseEntity.ok(SimpleResponse(false, 200, "password_reset allowed...."))
        } else {
            throw UserNotFoundException("${forgotPasswordDto.email} is not Registered...")
        }
    }

    override fun googleLogin(socialLoginDto: SocialLoginDto): ResponseEntity<Any> {
        return socialLogin(
            socialLoginDto.firstName,
            socialLoginDto.email,
            socialLoginDto.pic_url,
            "qwertyuiop",
            "google"
        )
    }

    override fun facebookLogin(socialLoginDto: SocialLoginDto): ResponseEntity<Any> {
        return socialLogin(
            socialLoginDto.firstName,
            socialLoginDto.email,
            socialLoginDto.pic_url,
            "qwertyuiop",
            "facebook"
        )
    }


    //helper functions
    fun encodePassword(password: String, confirmPassword: String): String {
        if (password != confirmPassword) throw MatchNotFoundException("password  must be the same.")
        return BCryptPasswordEncoder().encode(password)
    }

    fun createRoleIfNotFound(name: String, privileges: Privilege): Role {
        val r = roleRepository.findByName(name)
        return if (r.isPresent) {
            r.get()
        } else {
            val role = Role(name = name)
            role.privileges = privileges
            roleRepository.save(role)
        }
    }

    fun createPrivilegeIfNotFound(name: String): Privilege {
        val p = privilegeRepository.findByName(name)
        return if (p.isPresent) {
            p.get()
        } else {
            val privilege = Privilege(name = name)
            privilegeRepository.save(privilege)
        }
    }

    fun socialLogin(name: String, email: String, pic_url: String, password: String, type: String): ResponseEntity<Any> {
        val data = userRepository.findByEmail(email)
        val privilege = createPrivilegeIfNotFound(name = Permissions.READ_PRIVILEGE)
        val roles = createRoleIfNotFound(name = Roles.ROLE_USER, privileges = privilege)
        if (!data.isPresent) {
            if (type == "google") {
                val user = User(
                    firstName = name,
                    lastName = "",
                    email = email,
                    picUrl = pic_url,
                    password = encodePassword(password, password),
                    isEnabled = true,
                    isAccountNonExpired = true,
                    isCredentialsNonExpired = true,
                    isAccountNonLocked = true,
                    roles = roles,
                    isGoogleLogin = true
                )
                userRepository.save(user)
                val authentication: Authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        email,
                        password
                    )
                )
                SecurityContextHolder.getContext().authentication = authentication
                val userDetails = authentication.principal as AuthUserDetails
                if (!authentication.isAuthenticated) throw UserNotFoundException("something went wrong... please try again later!")
                val token = jwtUtility.generateToken(userDetails)
                val userInfo = userDetails.users

                return ResponseEntity.ok(
                    LoginResponse(
                        error = false,
                        statusCode = 200,
                        message = "Registered Successfully!",
                        token = token,
                        data = LoginUserData(
                        id = user.id,
                        firstName = userInfo.firstName,
                        lastName = userInfo.lastName,
                        email = userInfo.email,
                        picUrl = userInfo.picUrl,
                        phoneNumber = userInfo.phoneNumber,
                        isGoogleLogin = userInfo.isGoogleLogin,
                        verification = VerificationData(
                            count = 0,
                            status = "none"
                        ),
                    )
                )
                )
            }
            if (type == "facebook") {
                val user = User(
                    firstName = name,
                    lastName = "",
                    email = email,
                    picUrl = pic_url,
                    password = encodePassword(password, password),
                    isEnabled = true,
                    isAccountNonExpired = true,
                    isCredentialsNonExpired = true,
                    isAccountNonLocked = true,
                    roles = roles,
                    isFacebookLogin = true
                )
                userRepository.save(user)
                val authentication: Authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        email,
                        password
                    )
                )
                SecurityContextHolder.getContext().authentication = authentication
                val userDetails = authentication.principal as AuthUserDetails
                if (!authentication.isAuthenticated) throw UserNotFoundException("something went wrong... please try again later!")
                val token = jwtUtility.generateToken(userDetails)
                val userInfo = userDetails.users
                return ResponseEntity.ok(
                    LoginResponse(
                        error = false,
                        statusCode = 200,
                        message = "Registered Successfully!",
                        token = token,
                        data =  LoginUserData(
                            id = user.id,
                            firstName = userInfo.firstName,
                            lastName = userInfo.lastName,
                            email = userInfo.email,
                            picUrl = userInfo.picUrl,
                            phoneNumber = userInfo.phoneNumber,
                            isGoogleLogin = userInfo.isGoogleLogin,
                            verification = VerificationData(
                                count = 0,
                                status = "none"
                            ),
                        )
                    )
                )
            }

        }

        val user = data.get()
        if ((user.isGoogleLogin == true && type == "google") || (user.isFacebookLogin == true && type == "facebook")) {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    email,
                    password
                )
            )
            SecurityContextHolder.getContext().authentication = authentication
            val userDetails = authentication.principal as AuthUserDetails
            if (!authentication.isAuthenticated) throw UserNotFoundException("something went wrong... please try again later!")
            val token = jwtUtility.generateToken(userDetails)
            val userInfo = userDetails.users
            var verificationCount = 0
            var verificationStatus = "none"
            val verifiedVerifications = user.verification!!.filter { it.status == "Verified" }
            val rejectedVerifications = user.verification!!.filter { it.status == "rejected" }
            val pendingVerifications = user.verification!!.filter { it.status == "pending" }
            verificationCount=verifiedVerifications.size+pendingVerifications.size
            if (verifiedVerifications.isNotEmpty()) {
                if (pendingVerifications.isNotEmpty()) {
                    verificationStatus = "pending"
                } else {
                    verificationStatus = "verified"
                }
            }else{
                if (pendingVerifications.isNotEmpty()) {
                    verificationStatus = "pending"
                }
            }
            return ResponseEntity.ok(
                LoginResponse(
                    error = false,
                    statusCode = 200,
                    message = "Successfully LoggedIn!",
                    token = token,
                    data = LoginUserData(
                        id = user.id,
                        firstName = userInfo.firstName,
                        lastName = userInfo.lastName,
                        email = userInfo.email,
                        picUrl = userInfo.picUrl,
                        phoneNumber = userInfo.phoneNumber,
                        isGoogleLogin = userInfo.isGoogleLogin,
                        verification = VerificationData(
                            count = verificationCount
                            ,
                            status = verificationStatus
                        ),
                    )
                )
            )
        } else {
            throw Exception("something went wrong..")
        }

    }

}