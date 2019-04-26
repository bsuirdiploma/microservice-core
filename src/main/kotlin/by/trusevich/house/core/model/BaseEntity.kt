package by.trusevich.house.core.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.DateSerializer
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.Date
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(

    @Id
    @set:JsonProperty(access = READ_ONLY)
    @GeneratedValue(strategy = IDENTITY)
    @ApiModelProperty(readOnly = true)
    var id: Long? = null,

    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    @JsonSerialize(using = DateSerializer::class)
    @ApiModelProperty(readOnly = true)
    var created: Date? = null,

    @LastModifiedDate
    @JsonProperty(access = READ_ONLY)
    @JsonSerialize(using = DateSerializer::class)
    @ApiModelProperty(readOnly = true)
    var updated: Date? = null,

    @LastModifiedBy
    @JsonProperty(access = READ_ONLY)
    @ApiModelProperty(readOnly = true)
    var updatedBy: String? = null

) : Serializable {

    companion object {

        private const val serialVersionUID = -5113807648793061185L
    }
}
