package com.rdktechnologies.skit.error


import com.rdktechnologies.skit.error.exceptions.AlreadyExistException
import com.rdktechnologies.skit.error.exceptions.FileNotFoundException
import com.rdktechnologies.skit.error.exceptions.InvalidTokenException
import com.rdktechnologies.skit.error.exceptions.UserNotFoundException
import com.rdktechnologies.skit.model.response.app.SimpleResponse
import io.jsonwebtoken.ExpiredJwtException
import org.hibernate.exception.ConstraintViolationException
import org.hibernate.exception.SQLGrammarException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.dao.InvalidDataAccessResourceUsageException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.format.DateTimeParseException
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ExceptionHandler() : ResponseEntityExceptionHandler() {

//    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
//        ex.bindingResult.allErrors.forEach { error ->
//           val fieldName = (error as FieldError).field
//          var  message = error.defaultMessage.toString()
//            message= "$fieldName $message"
//            return ResponseEntity.ok(SimpleResponse(true, status.value(), message))
//        }
//        return ResponseEntity.ok(SimpleResponse(true, status.value(), ""))
//
//    }
//
//    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
//            return ResponseEntity.ok(SimpleResponse(true, status.value(), ex.message))
//    }
//
//    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
//        return ResponseEntity.ok(SimpleResponse(true, status.value(),"Requested body missing"))
//    }
//
//    override fun handleTypeMismatch(ex: TypeMismatchException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
//        return ResponseEntity.ok(SimpleResponse(true, status.value(),ex.message))
//    }
//    @ExceptionHandler(Exception::class)
//    protected fun exception(ex: Exception): ResponseEntity<SimpleResponse> {
//        return ResponseEntity.ok(SimpleResponse(true, 404, ex.message))
//    }
//override fun handleNoHandlerFoundException(
//    ex: NoHandlerFoundException,
//    headers: HttpHeaders,
//    status: HttpStatus,
//    request: WebRequest
//): ResponseEntity<Any> {
//    return ResponseEntity.status(status).body(
//        ApiResponse(
//            error = true,
//            message = "Not found",
//            statusCode = status.value(),
//            data = null
//        )
//    )
//}
//
//    override fun handleMethodArgumentNotValid(
//        ex: MethodArgumentNotValidException,
//        headers: HttpHeaders,
//        status: HttpStatus,
//        request: WebRequest
//    ): ResponseEntity<Any> {
//        val fieldErrors = ex.bindingResult
//            .fieldErrors
//            .stream()
//            .map { field: FieldError -> "" + field.defaultMessage }
//            .collect(Collectors.toList())
//        val globalErrors = ex.bindingResult
//            .globalErrors
//            .stream()
//            .map { field: ObjectError -> "" + field.defaultMessage }
//            .collect(Collectors.toList())
//        val errors: MutableList<String> = ArrayList()
//        errors.addAll(globalErrors)
//        errors.addAll(fieldErrors)
//        val message = if (fieldErrors.size > 0) {
//            fieldErrors[0]
//        }
////        else
////       if (globalErrors.size > 0) {
////            globalErrors[0].toString()
////        }
//        else {
//            "Some field are missing or invalid"
//        }
//        val err = ApiResponse(
//            error = true,
//            message = message,
//            statusCode = HttpStatus.BAD_REQUEST.value(),
//            data = null
//        )
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err)
//    }
//
//
//    @ExceptionHandler(ConstraintViolationException::class)
//    fun handleConstraintViolationException(
//        ex: ConstraintViolationException,
//        request: WebRequest?
//    ): ResponseEntity<Any?>? {
//        val details: MutableList<String?> = ArrayList()
//        details.add(ex.message)
//        val err = ApiResponse(
//            error = true,
//            message = messageConvert(ex),
//            statusCode = HttpStatus.BAD_REQUEST.value(),
//            data = null
//        )
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err)
//    }
//
//
//    @ExceptionHandler(UserNotFoundException::class)
//    fun handleResourceNotFoundException(
//        ex: UserNotFoundException
//    ): ResponseEntity<Any> {
//        val err = ApiResponse(
//            error = true,
//            message = ex.message,
//            statusCode = HttpStatus.NOT_FOUND.value(),
//            data = null
//        )
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err)
//    }
//
//    @ExceptionHandler(InvalidTokenException::class)
//    fun handleInvalidTokenException(
//        ex: InvalidTokenException
//    ): ResponseEntity<Any> {
//        val err = ApiResponse(
//            error = true,
//            message = ex.message,
//            statusCode = HttpStatus.UNAUTHORIZED.value(),
//            data = null
//        )
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err)
//    }
//
//    override fun handleHttpMediaTypeNotSupported(
//        ex: HttpMediaTypeNotSupportedException,
//        headers: HttpHeaders,
//        status: HttpStatus,
//        request: WebRequest
//    ): ResponseEntity<Any> {
//        val err = ApiResponse(
//            error = true,
//            message = ex.message,
//            statusCode = status.value(),
//            data = null
//        )
//        return ResponseEntity.status(status).body(err)
//    }
//
//
//    override fun handleExceptionInternal(
//        ex: Exception,
//        body: Any?,
//        headers: HttpHeaders,
//        status: HttpStatus,
//        request: WebRequest
//    ): ResponseEntity<Any> {
//
//        return when (status.value()) {
//            HttpStatus.NOT_FOUND.value() -> {
//                ResponseEntity.status(status).body(
//                    ApiResponse(
//                        error = true,
//                        message = "Not found",
//                        statusCode = status.value(),
//                        data = null
//                    )
//                )
//            }
//            HttpStatus.FORBIDDEN.value() -> {
//                ResponseEntity.status(status).body(
//                    ApiResponse(
//                        error = true,
//                        message = "Requested resource is forbidden",
//                        statusCode = status.value(),
//                        data = null
//                    )
//                )
//            }
//            HttpStatus.INTERNAL_SERVER_ERROR.value() -> {
//                ResponseEntity.status(status).body(
//                    ApiResponse(
//                        error = true,
//                        message = "Internal server error",
//                        statusCode = status.value(),
//                        data = null
//                    )
//                )
//            }
//            HttpStatus.BAD_REQUEST.value() -> {
//                ResponseEntity.status(status).body(
//                    ApiResponse(
//                        error = true,
//                        message = "Bad request",
//                        statusCode = status.value(),
//                        data = null
//                    )
//                )
//            }
//            else -> ResponseEntity.status(status).body(
//                ApiResponse(
//                    error = true,
//                    message = "Something went wrong",
//                    statusCode = status.value(),
//                    data = null
//                )
//            )
//        }
//    }
//
//
//    override fun handleHttpRequestMethodNotSupported(
//        ex: HttpRequestMethodNotSupportedException,
//        headers: HttpHeaders,
//        status: HttpStatus,
//        request: WebRequest
//    ): ResponseEntity<Any> {
//        return ResponseEntity.status(status).body(
//            ApiResponse(
//                error = true,
//                message = "Request method not allowed",
//                statusCode = status.value(),
//                data = null
//            )
//        )
//    }
//
//    override fun handleHttpMessageNotReadable(
//        ex: HttpMessageNotReadableException,
//        headers: HttpHeaders,
//        status: HttpStatus,
//        request: WebRequest
//    ): ResponseEntity<Any> {
//        return ResponseEntity.status(status).body(
//            ApiResponse(
//                error = true,
//                message = "Request message not readable",
//                statusCode = status.value(),
//                data = null
//            )
//        )
//    }
//
//    override fun handleHttpMediaTypeNotAcceptable(
//        ex: HttpMediaTypeNotAcceptableException,
//        headers: HttpHeaders,
//        status: HttpStatus,
//        request: WebRequest
//    ): ResponseEntity<Any> {
//        return ResponseEntity.status(status).body(
//            ApiResponse(
//                error = true,
//                message = "Media type not acceptable",
//                statusCode = status.value(),
//                data = null
//            )
//        )
//    }
//
//
//    @ExceptionHandler(Exception::class)
//    fun handle(
//        ex: Exception?,
//        request: HttpServletRequest?, response: HttpServletResponse?
//    ): ResponseEntity<Any> {
//        val statusCode: HttpStatus
//        val message = when (ex) {
//            is IllegalAccessException -> {
//                statusCode = HttpStatus.UNAUTHORIZED
//                "Access denied"
//            }
//            is NullPointerException -> {
//                statusCode = HttpStatus.BAD_REQUEST
//                "Fields are missing"
//            }
//            is IllegalArgumentException -> {
//                statusCode = HttpStatus.BAD_REQUEST
//                "Bad request"
//            }
//            is DateTimeParseException -> {
//                statusCode = HttpStatus.BAD_REQUEST
//                "Date not valid"
//            }
//            is NoHandlerFoundException -> {
//                statusCode = HttpStatus.BAD_GATEWAY
//                "No found"
//            }
//            is AccessDeniedException -> {
//                statusCode = HttpStatus.UNAUTHORIZED
//                "Requested resource is forbidden"
//            }
//            is EmptyResultDataAccessException -> {
//                statusCode = HttpStatus.NOT_FOUND
//                "Data not available"
//            }
//            is NoSuchElementException -> {
//                statusCode = HttpStatus.NOT_FOUND
//                "Not data present"
//            }
//            is SQLGrammarException -> {
//                statusCode = HttpStatus.INTERNAL_SERVER_ERROR
//                "Internal server error"
//            }
//            is InvalidDataAccessApiUsageException -> {
//                statusCode = HttpStatus.BAD_REQUEST
//                "Data type is incorrect"
//            }
//            is org.springframework.security.access.AccessDeniedException -> {
//                statusCode = HttpStatus.UNAUTHORIZED
//                "Requested resource is forbidden"
//            }
//            is UserNotFoundException -> {
//                statusCode = HttpStatus.NOT_FOUND
//                ex.message
//            }
//            is InvalidDataAccessResourceUsageException -> {
//                statusCode = HttpStatus.UNAUTHORIZED
//                "Data not available"
//            }
//            is InvalidTokenException -> {
//                statusCode = HttpStatus.UNAUTHORIZED
//                ex.message
//            }
//            is FileNotFoundException -> {
//                statusCode = HttpStatus.NOT_FOUND
//                ex.message
//            }
//            is ExpiredJwtException -> {
//                statusCode = HttpStatus.UNAUTHORIZED
//                "Token expired"
//            }
//            is javax.validation.ConstraintViolationException -> {
//                statusCode = HttpStatus.BAD_REQUEST
//                messageConvert(ex as ConstraintViolationException)
//            }
//            is AlreadyExistException -> {
//                statusCode = HttpStatus.NOT_ACCEPTABLE
//                ex.message
//            }
//            else -> {
//                statusCode = HttpStatus.INTERNAL_SERVER_ERROR
//                "Something went wrong try again later"
//            }
//        }
//        return ResponseEntity.status(statusCode).body(
//            ApiResponse(
//                error = true,
//                message = message,
//                statusCode = statusCode.value(),
//                data = null
//            )
//        )
//    }
//
//    private fun messageConvert(ex: ConstraintViolationException) : String{
//        return try {
//            if (ex.message?.contains(":") == true){
//                val array =  ex.message?.split(":")
//                array?.get(1)!!
//            }else {
//                "Field is missing"
//            }
//        }catch (e:Exception){
//            "Field is missing"
//        }
//    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.status(status).body(
            SimpleResponse(
                error = true,
                message = "Not found",
                statusCode = status.value(),
                data = null
            )
        )
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val fieldErrors = ex.bindingResult
            .fieldErrors
            .stream()
            .map { field: FieldError -> "" + field.defaultMessage }
            .collect(Collectors.toList())
        val globalErrors = ex.bindingResult
            .globalErrors
            .stream()
            .map { field: ObjectError -> "" + field.defaultMessage }
            .collect(Collectors.toList())
        val errors: MutableList<String> = ArrayList()
        errors.addAll(globalErrors)
        errors.addAll(fieldErrors)
        val message = if (fieldErrors.size > 0) {
            fieldErrors[0]
        }
//        else
//       if (globalErrors.size > 0) {
//            globalErrors[0].toString()
//        }
        else {
            "Some field are missing or invalid"
        }
        val err = SimpleResponse(
            error = true,
            message = message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            data = null
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err)
    }


    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        ex: ConstraintViolationException,
        request: WebRequest?
    ): ResponseEntity<Any?>? {
        val details: MutableList<String?> = ArrayList()
        details.add(ex.message)
        val err = SimpleResponse(
            error = true,
            message = messageConvert(ex),
            statusCode = HttpStatus.BAD_REQUEST.value(),
            data = null
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err)
    }


    @ExceptionHandler(UserNotFoundException::class)
    fun handleResourceNotFoundException(
        ex: UserNotFoundException
    ): ResponseEntity<Any> {
        val err = SimpleResponse(
            error = true,
            message = ex.message,
            statusCode = HttpStatus.NOT_FOUND.value(),
            data = null
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err)
    }

    @ExceptionHandler(InvalidTokenException::class)
    fun handleInvalidTokenException(
        ex: InvalidTokenException
    ): ResponseEntity<Any> {
        val err = SimpleResponse(
            error = true,
            message = ex.message,
            statusCode = HttpStatus.UNAUTHORIZED.value(),
            data = null
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err)
    }

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val err = SimpleResponse(
            error = true,
            message = ex.message,
            statusCode = status.value(),
            data = null
        )
        return ResponseEntity.status(status).body(err)
    }


    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        return when (status.value()) {
            HttpStatus.NOT_FOUND.value() -> {
                ResponseEntity.status(status).body(
                    SimpleResponse(
                        error = true,
                        message = "Not found",
                        statusCode = status.value(),
                        data = null
                    )
                )
            }
            HttpStatus.FORBIDDEN.value() -> {
                ResponseEntity.status(status).body(
                    SimpleResponse(
                        error = true,
                        message = "Requested resource is forbidden",
                        statusCode = status.value(),
                        data = null
                    )
                )
            }
            HttpStatus.INTERNAL_SERVER_ERROR.value() -> {
                ResponseEntity.status(status).body(
                    SimpleResponse(
                        error = true,
                        message = "Internal server error",
                        statusCode = status.value(),
                        data = null
                    )
                )
            }
            HttpStatus.BAD_REQUEST.value() -> {
                ResponseEntity.status(status).body(
                    SimpleResponse(
                        error = true,
                        message = "Bad request",
                        statusCode = status.value(),
                        data = null
                    )
                )
            }
            else -> ResponseEntity.status(status).body(
                SimpleResponse(
                    error = true,
                    message = "Something went wrong",
                    statusCode = status.value(),
                    data = null
                )
            )
        }
    }


    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.status(status).body(
            SimpleResponse(
                error = true,
                message = "Request method not allowed",
                statusCode = status.value(),
                data = null
            )
        )
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.status(status).body(
            SimpleResponse(
                error = true,
                message = "Request message not readable",
                statusCode = status.value(),
                data = null
            )
        )
    }

    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.status(status).body(
            SimpleResponse(
                error = true,
                message = "Media type not acceptable",
                statusCode = status.value(),
                data = null
            )
        )
    }


    @ExceptionHandler(Exception::class)
    fun handle(
        ex: Exception?,
        request: HttpServletRequest?, response: HttpServletResponse?
    ): ResponseEntity<Any> {
        val statusCode: HttpStatus
        val message = when (ex) {
            is IllegalAccessException -> {
                statusCode = HttpStatus.UNAUTHORIZED
                "Access denied"
            }
            is NullPointerException -> {
                statusCode = HttpStatus.BAD_REQUEST
                "Fields are missing"
            }
            is IllegalArgumentException -> {
                statusCode = HttpStatus.BAD_REQUEST
                "Bad request"
            }
            is AlreadyExistException -> {
                statusCode = HttpStatus.NOT_FOUND
                ex.message
            }
            is DateTimeParseException -> {
                statusCode = HttpStatus.BAD_REQUEST
                "Date not valid"
            }
            is NoHandlerFoundException -> {
                statusCode = HttpStatus.BAD_GATEWAY
                "No found"
            }
            is AccessDeniedException -> {
                statusCode = HttpStatus.UNAUTHORIZED
                "Requested resource is forbidden"
            }
            is EmptyResultDataAccessException -> {
                statusCode = HttpStatus.NOT_FOUND
                "Data not available"
            }
            is NoSuchElementException -> {
                statusCode = HttpStatus.NOT_FOUND
                "Not data present"
            }
            is SQLGrammarException -> {
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR
                "Internal server error"
            }
            is InvalidDataAccessApiUsageException -> {
                statusCode = HttpStatus.BAD_REQUEST
                "Data type is incorrect"
            }
            is org.springframework.security.access.AccessDeniedException -> {
                statusCode = HttpStatus.UNAUTHORIZED
                "Requested resource is forbidden"
            }
            is UserNotFoundException -> {
                statusCode = HttpStatus.NOT_FOUND
                ex.message
            }
            is InvalidDataAccessResourceUsageException -> {
                statusCode = HttpStatus.UNAUTHORIZED
                "Data not available"
            }
            is InvalidTokenException -> {
                statusCode = HttpStatus.UNAUTHORIZED
                ex.message
            }
            is FileNotFoundException -> {
                statusCode = HttpStatus.NOT_FOUND
                ex.message
            }
            is ExpiredJwtException -> {
                statusCode = HttpStatus.UNAUTHORIZED
                "Token expired"
            }
            is javax.validation.ConstraintViolationException -> {
                statusCode = HttpStatus.BAD_REQUEST
                messageConvert(ex as ConstraintViolationException)
            }
//            is EmailAlreadyRegisterException -> {
//                statusCode = HttpStatus.NOT_ACCEPTABLE
//                ex.message
//            }
            else -> {
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR
                "Something went wrong try again later"
            }
        }
        return ResponseEntity.status(statusCode).body(
            SimpleResponse(
                error = true,
                message = message,
                statusCode = statusCode.value(),
                data = null
            )
        )
    }

    private fun messageConvert(ex: ConstraintViolationException) : String{
        return try {
            if (ex.message?.contains(":") == true){
                val array =  ex.message?.split(":")
                array?.get(1)!!
            }else {
                "Field is missing"
            }
        }catch (e:Exception){
            "Field is missing"
        }
    }

}
