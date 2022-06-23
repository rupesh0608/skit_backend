package com.rdktechnologies.skit.service.dowload

import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface IDownloadService {
    fun downloadFile(fileName: String):ResponseEntity<Resource>
}