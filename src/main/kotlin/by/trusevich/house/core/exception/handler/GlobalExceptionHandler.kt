package by.trusevich.house.core.exception.handler

import by.trusevich.house.core.exception.EntityNoContentException
import by.trusevich.house.core.exception.EntityNotFoundException
import by.trusevich.house.core.exception.UnauthorizedException
import by.trusevich.house.core.util.toResponse
import by.trusevich.house.core.util.toValidationResponse
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EmptyResultDataAccessException::class, EntityNotFoundException::class)
    fun handleNotFound(ex: Exception) = toResponse(ex, NOT_FOUND)

    @ExceptionHandler
    fun handleNoContent(ex: EntityNoContentException) = toResponse(ex, NO_CONTENT)

    @ExceptionHandler
    fun handleUnauthorized(ex: UnauthorizedException) = toResponse(ex, UNAUTHORIZED)

    @ExceptionHandler
    fun handleOtherExceptions(ex: Exception) = toResponse(ex, INTERNAL_SERVER_ERROR)

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ) = toValidationResponse(ex)

}
