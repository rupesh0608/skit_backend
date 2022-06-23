package com.rdktechnologies.skit.utils


import com.rdktechnologies.skit.error.exceptions.InvalidFileTypeException
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

class FileHelper {
    fun getExtension(file: MultipartFile): String? {
        return StringUtils.getFilenameExtension(file.originalFilename)
    }

    fun getContentType(file: MultipartFile): String? {
        return if (file.contentType == "video/mp4") {
            "video"
        } else if (file.contentType == "image/jpeg" || file.contentType == "image/png" || file.contentType == "image/jpg") {
            "image"
        } else {
            val ext = getExtension(file)
            throw  InvalidFileTypeException("files with $ext not allowed")
        }

    }
    fun createFileAndGetUrl(file: MultipartFile): String {
        val uuid = UUID.randomUUID()
        val extension = FileHelper().getExtension(file)
        val path = Paths.get(File("${uploadLocation}${File.separator}$uuid.$extension").toURI())
        val fileUrl = "/download/$uuid.$extension"
        Files.copy(file.inputStream, path, StandardCopyOption.REPLACE_EXISTING)
        return fileUrl
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