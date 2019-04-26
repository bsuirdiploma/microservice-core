package by.trusevich.house.core.config

import by.trusevich.house.core.util.USER_PRINCIPAL
import by.trusevich.house.core.util.getHttpRequestAttribute
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.Optional.ofNullable

@Component
class SystemLoggedInUserAuditorAware : AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> = ofNullable(getHttpRequestAttribute<String>(USER_PRINCIPAL))
}