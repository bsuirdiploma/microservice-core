package by.trusevich.house.core.exception.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.io.Serializable

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ObjectErrorResource(

    var resource: String? = null,

    var field: String? = null,

    var code: String? = null,

    var message: String? = null

) : Serializable {

    companion object {

        private const val serialVersionUID = -5951167653951170872L
    }

}
