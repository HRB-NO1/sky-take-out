package com.sky.Aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Auto fill the field with the current user and current time
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * Define the pointcut for the auto fill
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillCutPoint() {}

    @Before("autoFillCutPoint()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("Auto fill the field with the current user and current time");

        // Get the method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // Get the method signature
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); // Get the annotation
        OperationType operationType = autoFill.value(); // Get the operation type

        // Get the arguments
        Object[] args = joinPoint.getArgs(); // Get the arguments
        if(args == null || args.length == 0) {
            return;
        }

        Object entity = args[0];

        // Get the current user
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        // Fill the field
        if(operationType == OperationType.INSERT) {
            try {
                Method SetCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method SetUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method SetCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method SetUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                SetCreateTime.invoke(entity, now);
                SetUpdateTime.invoke(entity, now);
                SetCreateUser.invoke(entity, currentId);
                SetUpdateUser.invoke(entity, currentId);
                } catch (Exception e) {
                throw new RuntimeException(e);
                }
            } else if(operationType == OperationType.UPDATE) {
                try {
                    Method SetUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    Method SetUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                    SetUpdateTime.invoke(entity, now);
                    SetUpdateUser.invoke(entity, currentId);
                    } catch (Exception e) {
                    throw new RuntimeException(e);
                    }
            }
        }
    }
