package com.maids.library_system.infrastructure.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Define the pointcut for service layer methods
    @Pointcut("within(com.maids.library_system.*.services..*)")
    public void serviceLayer() {}

    // Define the logging for method calls and performance metrics
    @Around("serviceLayer()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;

        try {
            // Log method entry
            logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());

            // Proceed with method execution
            result = joinPoint.proceed();

            // Log method exit
            logger.info("Exiting method: {} with result: {}", joinPoint.getSignature(), result);
        } catch (Exception e) {
            // Log exceptions if thrown
            logger.error("Exception in method: {} with cause: {}", joinPoint.getSignature(), e.getMessage(), e);
            throw e;
        } finally {
            // Log the time taken to execute the method
            long timeTaken = System.currentTimeMillis() - startTime;
            logger.info("Method: {} executed in {} ms", joinPoint.getSignature(), timeTaken);
        }

        return result;
    }

    // Define the logging for exceptions
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "exception")
    public void logAfterThrowing(Exception exception) {
        logger.error("Exception occurred: {}", exception.getMessage());
    }
}
