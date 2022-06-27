package com.rdktechnologies.skit.repository

import com.rdktechnologies.skit.entity.Document
import com.rdktechnologies.skit.entity.Eligibility
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EligibilityRepository : JpaRepository<Eligibility,Long> {
}