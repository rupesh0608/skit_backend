package com.rdktechnologies.skit.service.verification

import com.rdktechnologies.skit.model.dto.app.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface IVerificationService {
    fun uploadDocuments(
        userId:Long?=null,
        document10: Optional<MultipartFile>? = null,
        document12: Optional<MultipartFile>? = null,
        documentGraduation: Optional<MultipartFile>? = null,
        documentCertificates: Optional<MultipartFile>? = null,
    ): ResponseEntity<Any>
}