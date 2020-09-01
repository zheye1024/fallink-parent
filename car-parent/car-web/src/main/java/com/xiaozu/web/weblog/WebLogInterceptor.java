package com.xiaozu.web.weblog;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Log4j2
@Aspect
@Component
public class WebLogInterceptor {
    //系统换行符，用于日志打印
    private final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 定义拦截规则：拦截controller包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* *.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    protected void controllerMethodPointcut() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("controllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        //参数
        Object[] args = pjp.getArgs();
        Set<Object> params = new HashSet<>();
        //请求地址
        String path = "";
        if (args != null) {
            for (Object parm : args) {
                if (parm instanceof HttpServletRequest) {
                    HttpServletRequest request = (HttpServletRequest) parm;
                    path = request.getRequestURI();
                } else if (parm instanceof HttpServletResponse) {
                } else {
                    params.add(parm);
                }
            }
        }
        if (path == null || "".equals(path)) {
            path = this.getPath(pjp);
        }
        StringBuilder sb = new StringBuilder();
        Object result = "----";
        long beginTime = System.currentTimeMillis();
        try {
            sb.append(LINE_SEPARATOR).append("********************************************************************************************");
            sb.append(LINE_SEPARATOR).append("请求方法>>").append(path);
            sb.append(LINE_SEPARATOR).append("参数>>").append(params);
            //执行方法
            result = pjp.proceed();
            return result;
        } catch (Throwable e) {
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long ms = endTime - beginTime;
            sb.append(LINE_SEPARATOR).append("耗时>>").append(ms).append("毫秒");
            sb.append(LINE_SEPARATOR).append("结果>>").append(result);
            sb.append(LINE_SEPARATOR).append("============================================================================================");
            log.info(sb.toString());
        }
    }

    private String getPath(ProceedingJoinPoint pjp) {

        Class doClass = pjp.getSignature().getDeclaringType();
        Object classReq = doClass.getDeclaredAnnotation(RequestMapping.class);
        //获取请求地址路径，有时路径写在class上的，需要与方法的拼接
        String path1 = "";
        if (classReq != null && classReq instanceof RequestMapping) {
            String val[] = ((RequestMapping) classReq).value();
            if (val.length > 0) {
                path1 = val[0];
            }
        }
        // 获取方法上的路径
        String method = pjp.getSignature().getName();
        Method[] methods = doClass.getMethods();
        String path2 = "";
        for (Method m : methods) {
            if (method.equals(m.getName())) {
                RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
                if (requestMapping != null) {
                    if (requestMapping.value() != null && requestMapping.value().length > 0) {
                        path2 = requestMapping.value()[0];
                    }
                }
                break;
            }
        }
        return path1 + path2;
    }
}
