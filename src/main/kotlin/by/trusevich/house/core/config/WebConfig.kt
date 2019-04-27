package by.trusevich.house.core.config

import by.trusevich.house.core.service.BaseAuthenticationService
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class WebConfig(private val baseAuthenticationService: BaseAuthenticationService) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuthenticationInterceptor(baseAuthenticationService))
            .excludePathPatterns("/auth")
    }
}