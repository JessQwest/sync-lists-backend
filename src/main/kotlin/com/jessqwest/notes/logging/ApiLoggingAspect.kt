package com.jessqwest.notes.logging

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.JoinPoint
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Aspect
@Component
class ApiLoggingAspect {

    private val logger: Logger = Logger.getLogger(ApiLoggingAspect::class.java.name)

    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    fun logApiCall(joinPoint: JoinPoint) {
        val methodName = joinPoint.signature.name
        val args = joinPoint.args.joinToString(", ") { it.toString() }
        logger.info("API called: $methodName with arguments: $args")
    }
}