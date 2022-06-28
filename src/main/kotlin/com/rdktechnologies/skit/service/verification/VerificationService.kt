package com.rdktechnologies.skit.service.verification

import com.rdktechnologies.skit.entity.Document
import com.rdktechnologies.skit.entity.DocumentVerification
import com.rdktechnologies.skit.error.exceptions.UserNotFoundException
import com.rdktechnologies.skit.model.response.adminpannel.VerificationList
import com.rdktechnologies.skit.model.response.adminpannel.VerificationListResponse
import com.rdktechnologies.skit.model.response.adminpannel.verificationUserProfile
import com.rdktechnologies.skit.model.response.app.SimpleResponse
import com.rdktechnologies.skit.repository.DocumentRepository
import com.rdktechnologies.skit.repository.DocumentVerificationRepository
import com.rdktechnologies.skit.repository.UserRepository
import com.rdktechnologies.skit.utils.FileHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class VerificationService : IVerificationService {

    @Autowired
    lateinit var documentVerificationRepository: DocumentVerificationRepository

    @Autowired
    lateinit var documentRepository: DocumentRepository

    @Autowired
    lateinit var userRepository: UserRepository
    override fun uploadDocuments(
        userId: Long?,
        document10: Optional<MultipartFile>?,
        document12: Optional<MultipartFile>?,
        documentGraduation: Optional<MultipartFile>?,
        documentCertificates: Optional<MultipartFile>?
    ): ResponseEntity<Any> {
        val data = userRepository.findById(userId!!)
        if (!data.isPresent) throw UserNotFoundException("User Not Found")
        val user = data.get()
        val documents = mutableListOf<Document>()
        if (document10?.isPresent!!) {
            val profileData = document10.get()
            if (!profileData.isEmpty && profileData.contentType?.contains("image") == true) {
                val d = Document(
                    type = "10th Result",
                    documentUrl = "https://s-kit.herokuapp.com${FileHelper().createFileAndGetUrl(profileData)}"
                )
                documentRepository.save(d)
                documents.add(d)

            }

        }
        if (document12?.isPresent!!) {
            val profileData = document12.get()
            if (!profileData.isEmpty && profileData.contentType?.contains("image") == true) {
                val d = Document(
                    type = "12th Result",
                    documentUrl = "https://s-kit.herokuapp.com${FileHelper().createFileAndGetUrl(profileData)}"
                )
                documentRepository.save(d)
                documents.add(d)
            }
        }

        if (documentGraduation?.isPresent!!) {
            val profileData = documentGraduation.get()
            if (!profileData.isEmpty && profileData.contentType?.contains("image") == true) {
                val d = Document(
                    type = "Graduation Result",
                    documentUrl = "https://s-kit.herokuapp.com${FileHelper().createFileAndGetUrl(profileData)}"
                )
                documentRepository.save(d)
                documents.add(d)
            }
        }

        if (documentCertificates?.isPresent!!) {
            val profileData = documentCertificates.get()
            if (!profileData.isEmpty && profileData.contentType?.contains("image") == true) {
                val d = Document(
                    type = "Experience Certificates",
                    documentUrl = "https://s-kit.herokuapp.com${FileHelper().createFileAndGetUrl(profileData)}"
                )
                documentRepository.save(d)
                documents.add(d)
            }
        }
        val documentObj = DocumentVerification(
            documents = documents,
            status = "pending",
            user = user
        )
        documentVerificationRepository.save(documentObj)
        val v = mutableListOf<DocumentVerification>()
        v.add(documentObj)
        user.verification = v
        userRepository.save(user)
        return ResponseEntity.ok(SimpleResponse(false, 200, "Documents Successfully Sent for Verification"))
    }

    override fun getVerificationList(): ResponseEntity<Any> {
        val data = documentVerificationRepository.findAll()
        val list: MutableList<VerificationList>? = mutableListOf()
        for (item in data) {
            val userData = userRepository.findById(item.user!!.id!!)
            if (userData.isPresent) {
                val profileData = userData.get()
                list?.add(
                    VerificationList(
                        id=item.id,
                        profile = verificationUserProfile(
                            id=profileData.id,
                            name = "${profileData.firstName} ${profileData.lastName}",
                            email = profileData.email,
                            picUrl = profileData.picUrl,
                            phoneNumber = profileData.phoneNumber
                        ),
                        documents = item.documents!!,
                        status = item.status,
                        message = item.message
                    )
                )
            }
        }
        return ResponseEntity.ok(VerificationListResponse(false,"",list))
    }


}