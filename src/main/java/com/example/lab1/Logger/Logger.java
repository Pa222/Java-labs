package com.example.lab1.Logger;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
public class Logger {

    @Pointcut("execution(* com.example.lab1.*.*(..))")
    public void selectAllMethodsAvailable(){}

    @Around("selectAllMethodsAvailable()")
    public Object afterAdvice(ProceedingJoinPoint thisJoinPoint) throws Throwable{
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();
        log.info("Method: " + methodName + ", args: " + Arrays.toString(methodArgs));
        Object result = thisJoinPoint.proceed();
        log.info("Method " + methodName + " returns " + result);

        return result;
    }
}
