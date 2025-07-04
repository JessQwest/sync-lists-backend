package com.jessqwest.notes.logging

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.JoinPoint
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Aspect
@Component
class ApiLoggingAspect {

    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    fun logApiCall(joinPoint: JoinPoint) {
        val methodName = joinPoint.signature.name
        val args = joinPoint.args.joinToString(", ") { it.toString() }
        println("API called: $methodName with arguments: $args")
    }
}

@Bean
fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
    return CommandLineRunner {
        println("Beans provided by Spring Boot:")
        ctx.beanDefinitionNames
                .filter { it.contains("controller", ignoreCase = true) }
                .sorted()
                .forEach { println(it) }
    }
}