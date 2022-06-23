package com.rdktechnologies.skit.repository

import com.rdktechnologies.skit.entity.Service
import com.rdktechnologies.skit.entity.ServiceModule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ServiceRepository : JpaRepository<Service,Long> {
}