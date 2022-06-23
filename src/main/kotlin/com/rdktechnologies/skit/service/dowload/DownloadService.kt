package com.rdktechnologies.skit.service.dowload

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import sun.rmi.runtime.Log
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*


@Service
class DownloadService:IDownloadService{
    override fun downloadFile(fileName: String): ResponseEntity<Resource> {
        val path = Paths.get(uploadLocation).toAbsolutePath().resolve(fileName)
        val resource = UrlResource(path.toUri())
        if (resource.exists() && resource.isReadable){
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline;fileName"+resource.filename)
                    .body(resource)
        }else{
            throw RuntimeException("file is not available... $fileName")
        }
    }




    init {
        val uploadPath = Paths.get("download")
        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath)
        }
    }

    companion object {
        var uploadLocation = "download"
    }


}