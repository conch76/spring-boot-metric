package com.conch.metrics.aop;

import com.conch.metrics.config.MetricsAutoConfig;
import com.conch.metrics.config.MetricsControllerConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Conditional(value = {MetricsAutoConfig.class, MetricsControllerConfig.class})
@Slf4j
public class ControllerMetricsAspect {

    @Pointcut("within(@(@org.springframework.stereotype.Controller *) *)")
    public void controller() {
    }

    // NOT IMPLEMENTED YET!!!
    @Around("controller() && execution(* *.*(..))" + "@@annotation()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.warn("Auto controller aspect for metrics not implemented yet");
        return joinPoint.proceed();
    }
}
