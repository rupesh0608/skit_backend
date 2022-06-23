package com.rdktechnologies.skit.controller


import com.rdktechnologies.skit.service.dowload.IDownloadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/download/")
class DownloadController {

    @Autowired
    private lateinit var downloadService: IDownloadService

    @GetMapping("/{fileName}")
    fun downloadFile(@PathVariable("fileName")fileName:String): ResponseEntity<Resource> {
        return downloadService.downloadFile(fileName)
    }
}