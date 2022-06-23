package com.rdktechnologies.skit.repository

import com.rdktechnologies.skit.entity.DocumentVerification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentVerificationRepository : JpaRepository<DocumentVerification,Long> {
}