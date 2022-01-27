package com.recruitment.casttest.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class LoggingAspect {
    /**
     * Pointcut that matches all REST endpoints.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void apiPointcut() {
        //Before API call
    }

    /**
     * Advice that logs methods before each call.
     *
     * @param joinPoint join point for advice
     */
    @Before(value = "apiPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("-----Start---- in {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    /**
     * Advice that logs methods after each call.
     *
     * @param joinPoint join point for advice
     */
    @After(value = "apiPointcut()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("-----End----- in {}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "apiPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getMessage());
    }

}
