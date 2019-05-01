package by.trusevich.house.core.util

import by.trusevich.house.core.exception.model.ErrorDetails
import by.trusevich.house.core.exception.model.ObjectErrorResource
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import java.util.Date

const val VALIDATION_REASON = "Model validation failed"

private val log by lazyLogger("ExceptionHandlingUtils")

@Suppress("unused")
const val SC_UNPROCESSABLE_ENTITY = 422

fun toResponse(ex: Exception, status: HttpStatus): ResponseEntity<ErrorDetails> = status(status).body(toResponse(ex, ex.message, status))

fun toValidationResponse(ex: MethodArgumentNotValidException): ResponseEntity<Any> =
    status(UNPROCESSABLE_ENTITY).contentType(APPLICATION_JSON_UTF8).body(
        toResponse(ex,
            VALIDATION_REASON,
            UNPROCESSABLE_ENTITY,
            ex.bindingResult.allErrors.mapWithTransform { from, to ->
                to.resource = from.objectName
                to.field = (from as FieldError).field
                to.code = from.code
                to.message = from.defaultMessage
            }) as Any
    )

@Suppress("UNCHECKED_CAST")
private fun toResponse(
    ex: Exception, message: String?, status: HttpStatus, errors: List<ObjectErrorResource>? = null
) =
    ErrorDetails(Date(), message, status.reasonPhrase, ex.javaClass.canonicalName, getHttpRequestUri(), errors)
        .also { log.error(it.toJson(), ex) }
