package com.rdktechnologies.skit.service.jobs

import com.rdktechnologies.skit.model.dto.app.*
import com.rdktechnologies.skit.model.dto.adminpannel.JobDto
import com.rdktechnologies.skit.model.dto.adminpannel.UpdateJobDto
import org.hibernate.engine.jdbc.env.spi.AnsiSqlKeywords
import org.springframework.http.ResponseEntity

interface IJobsService {
 fun saveJob(jobsDto: JobDto):ResponseEntity<Any>
 fun updateJob(updateJobDto: UpdateJobDto):ResponseEntity<Any>
 fun publishJob(id:Long):ResponseEntity<Any>
 fun unPublishJob(id:Long):ResponseEntity<Any>
 fun getAllJob():ResponseEntity<Any>
 fun createEligibility(keyword:String):ResponseEntity<Any>
 fun getAllEligibility():ResponseEntity<Any>
}