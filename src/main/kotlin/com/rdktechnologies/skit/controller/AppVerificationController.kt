package com.rdktechnologies.skit.controller


import com.rdktechnologies.skit.service.verification.VerificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.Optional


@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/app/verification/")
class AppVerificationController {

    @Autowired
    private lateinit var verificationService: VerificationService

    @PostMapping("/upload_documents")
    fun uploadDocuments(
        @RequestParam(required = true) userId:Long?=null,
        @RequestParam document10: Optional<MultipartFile>? = null,
        @RequestParam document12: Optional<MultipartFile>? = null,
        @RequestParam documentGraduation: Optional<MultipartFile>? = null,
        @RequestParam documentCertificates: Optional<MultipartFile>? =null,
    ): ResponseEntity<Any> {
        return verificationService.uploadDocuments(userId,document10,document12,documentGraduation,documentCertificates)
    }




}