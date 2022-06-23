package com.rdktechnologies.skit.security

import com.rdktechnologies.skit.service.UserService
import com.rdktechnologies.skit.utils.JWTFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var unauthorizedEntryPoint: UnauthorizedEntryPoint

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtFilter: JWTFilter

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService)
    }

   // @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }


    override fun configure(http: HttpSecurity) {
//        http.csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/swagger-ui/**",
//                        "/v3/api-docs",
//                    "/v3/api-docs/**",
//                    "/api/app/**",
//                        "/api/download/**",
//                    "/api/admin/**"
//
//                )
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        http?.headers()?.frameOptions()?.sameOrigin()?.and()?.csrf()?.disable()?.authorizeRequests()
            ?.antMatchers(
                "/api/auth/**",
                "/swagger-ui/**",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "api/app/**",
                "/api/download/**"
            )
            ?.permitAll()?.antMatchers("/api")?.authenticated()?.and()?.exceptionHandling()
            ?.authenticationEntryPoint(unauthorizedEntryPoint)?.and()?.sessionManagement()
            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ?.and()?.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}