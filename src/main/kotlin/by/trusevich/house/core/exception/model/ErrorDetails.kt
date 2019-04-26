package by.trusevich.house.core.exception.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.io.Serializable
import java.util.Date

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ErrorDetails(

    val timestamp: Date? = null,

    val message: String? = null,

    val status: String? = null,

    val exception: String? = null,

    val path: String? = null,

    val errors: List<ObjectErrorResource>? = null

) : Serializable {

    companion object {

        private const val serialVersionUID = 1272596646360332020L
    }

}
