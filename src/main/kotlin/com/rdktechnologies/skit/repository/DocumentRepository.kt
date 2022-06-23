package com.rdktechnologies.skit.repository

import com.rdktechnologies.skit.entity.Document
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentRepository : JpaRepository<Document,Long> {
}