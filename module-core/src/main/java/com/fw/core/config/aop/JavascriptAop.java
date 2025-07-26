package com.fw.core.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

/*
    Javascript AOP
 */
@Slf4j
@Aspect
@Component
public class JavascriptAop {

    @Pointcut("execution(* com.fw..*Controller.*(..))")
    private void pointcut() {}

    @AfterReturning(value = "pointcut()", returning = "returnObject")
	public void afterReturning(JoinPoint joinPoint, Object returnObject) {
        String viewName = String.valueOf(returnObject);
        ModelMap map = null;

        for(Object obj : joinPoint.getArgs()) {
            if(obj instanceof ModelAndView){
                viewName = ((ModelAndView) obj).getViewName();
                map = ((ModelAndView) obj).getModelMap();
            } else if (obj instanceof ModelMap){
                map = (ModelMap) obj;
            }
        }

        String jsName = viewName.replaceAll("/?(\\w+)([\\w/-]+)", "/static/$1$2.js");

        if(StringUtils.isNotBlank(viewName)) {
            if (map == null) {
                return;
            }

            map.put("jsName", jsName);
            log.info("Javascript 파일명 : {}", jsName);
        }
	}

}
