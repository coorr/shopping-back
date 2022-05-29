package shopping.coor.infra.log;

import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class ProxyAspect {

    private final LogTrace trace;

//    @Pointcut("execution(* shopping.coor..*(..))")
//    private void coor() {}
//
//    @Pointcut("execution(* shopping.coor..*controller..*(..))")
//    private void controller() {}
//
//    @Pointcut("execution(* shopping.coor..*repository..*(..))")
//    private void repository() {}
//
//    @Pointcut("execution(* shopping.coor..*serviceImpl..*(..))")
//    private void serviceImpl() {}
//
//    @Around("controller() || repository() || serviceImpl()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = trace.begin(message);
            // 비즈니스 로직 호출
            Object result = joinPoint.proceed();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            Sentry.captureMessage(e.getMessage());
            throw e;
        }
    }
}
