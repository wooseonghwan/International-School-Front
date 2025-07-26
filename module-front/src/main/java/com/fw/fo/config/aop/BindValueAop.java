/*
package com.fw.fo.config.aop;

import com.fw.core.dto.CommonDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

*/
/*
    BindVAlue AOP
 *//*

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class BindValueAop {

    @Value("${bo.session.key-name}")
    private String FO_SESSION_KEY;

    @Around("execution(* com.fw..*Controller.*(..))")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        ModelMap map = null;
        CommonDTO commonDTO = null;

        for(Object obj : proceedingJoinPoint.getArgs()) {
            if(obj instanceof CommonDTO){
                commonDTO = (CommonDTO) obj;
            } else if (obj instanceof HttpServletRequest) {
                request = (HttpServletRequest) obj;
            } else if (obj instanceof ModelMap) {
                map = (ModelMap) obj;
            }
        }

        //for(Object obj : proceedingJoinPoint.getArgs()) {
        //    if(obj instanceof CommonDTO) {
        //        if (commonDTO != null) {
        //            ((CommonDTO) obj).setFrontSession((FoSessionDTO) request.getSession().getAttribute(FO_SESSION_KEY));
        //        }
        //    }
        //}

        return proceedingJoinPoint.proceed();
	}

}
*/
