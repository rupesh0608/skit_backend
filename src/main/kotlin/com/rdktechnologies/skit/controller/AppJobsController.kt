package com.rdktechnologies.skit.controller


import com.rdktechnologies.skit.model.dto.app.*
import com.rdktechnologies.skit.model.dto.adminpannel.JobDto
import com.rdktechnologies.skit.model.dto.adminpannel.UpdateJobDto
import com.rdktechnologies.skit.model.dto.adminpannel.publish_unpublish_JobDto
import com.rdktechnologies.skit.service.auth.IAuthService
import com.rdktechnologies.skit.service.jobs.JobsService
import com.rdktechnologies.skit.service.users.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid



@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/app/jobs/")
class AppJobsController {

    @Autowired
    private lateinit var authService : IAuthService
    @Autowired
    private lateinit var jobsService: JobsService

    @GetMapping("/all_eligible_jobs/{userId}")
    fun getAllEligibleJobs(@PathVariable(name = "userId") userId:Long,):ResponseEntity<Any>{

        return jobsService.getAllJob()
    }

}