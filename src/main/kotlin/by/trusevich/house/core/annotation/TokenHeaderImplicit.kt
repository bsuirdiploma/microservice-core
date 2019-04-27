package by.trusevich.house.core.annotation

import by.trusevich.house.core.util.TOKEN_HEADER
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import java.lang.annotation.Inherited
import kotlin.annotation.AnnotationTarget.FUNCTION

@Retention
@Inherited
@Target(FUNCTION)
@MustBeDocumented
@ApiImplicitParams(
    ApiImplicitParam(
        "aa.bb.cc", paramType = "header", required = true,
        name = TOKEN_HEADER, dataTypeClass = String::class, type = "string"
    )
)
@Suppress("unused")
annotation class TokenHeaderImplicit