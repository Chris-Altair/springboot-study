package com.study.annection;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Before不支持ProceedingJoinPoint
 */
@Aspect
@Component
public class VerificationAspect {
    private static final Logger logger = LoggerFactory.getLogger(VerificationAspect.class);

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUKhlxvfHmnQKCehllSyMKzfqerVIJbTNj2hQXA8TEwlyFfp+BPq5uEqffnNec6DERKVYKBqG99zXfzqHUQdTOmyT2ubzYMSrl0q1l1l8TUG1eXzO8mymQBOx5ZGYPjBKRl0xi02q1sW0Ri+Z/C2QnqFz92ZVUN5aNufkFOmPWUQIDAQAB";
    private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANQqGXG98eadAoJ6GWVLIwrN+p6tUgltM2PaFBcDxMTCXIV+n4E+rm4Sp9+c15zoMREpVgoGob33Nd/OodRB1M6bJPa5vNgxKuXSrWXWXxNQbV5fM7ybKZAE7HlkZg+MEpGXTGLTarWxbRGL5n8LZCeoXP3ZlVQ3lo25+QU6Y9ZRAgMBAAECgYA6UgRChuMa8GgDM8sNVBUi4DIXMi+N1c29kfHZzbhmGcNTEtaHsZJDQa+RwUxnnk/2lrVG9kG5tV3C0ssbTKlRTX0G1BCxHkyUki/X+f12fZcXvVgrDcGXrojTyK/mK1yUZDID2LlOGwm/XxQiVEsG9UYKys6W0JKiD90aplvg3QJBAPT6TUZVbTiEgRwgFubuSvCBWjDjsWcjMULPkKJwezNYb5PDpVZ1FNzvpR14NrR7+9a5KskQynJNSP2ken3/4n8CQQDdtdkccXnoEhwk3Hx7YM2splLtulFKNlo1iV9dSINnc921LB3IB+B1kRIZNDaSB/+0ovTqcVTDfUT8AS0yVD8vAkEAhjtYeD9610sTi5uxsNvFSD84ci1xGXhZF6iwMbG8cAf3K1o6kEMbKWxOZvVl1ENTXPadEQsYQsVCR2kGjV/WdQJBAJt97Q3YvAG5qclkZV12T+kVITYfJOHiarP/a0WWFKqahVSJ4z0l4RMzMjlA+EDEoV6DpzdBUoxkL9x5ysPwd/sCQCB4ufYc1qf0/ms4Sq2eDd57iJL5+Qv2hZJBKDVtWQ/weKPZofQaT45AZdvm9dseYJvMFrf+4sOWM4QHFoc6+fA=";

    @Pointcut("@annotation(com.study.annection.Verification)")
    public void verificationPointCut() {
    }

//    @Before("verificationPointCut()")
//    public void before(JoinPoint point) throws Throwable {
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        String className = point.getTarget().getClass().getName();
//        String methodName = signature.getName();
//        // 请求的参数
//        Object[] args = point.getArgs();
//        StringBuilder argsStr = new StringBuilder();
//        for (Object o: args){
//            argsStr.append(o.toString()).append(",");
//        }
//        logger.info("before-{}:args:{}",new Object[]{className+":"+methodName, argsStr.toString()});
//    }

    @Around("verificationPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
//        Method method = signature.getMethod();
//        Verification verification = method.getAnnotation(Verification.class);
        StringBuilder apiName = new StringBuilder();
        apiName.append(point.getTarget().getClass().getName())  //请求的类名
                .append(".")
                .append(signature.getName());   //请求的方法名
        logger.debug("当前访问api:{}", apiName.toString());

        // 请求的参数
        Object[] args = point.getArgs();
        String[] argNames = signature.getParameterNames();
        Map<String, Object> params = new LinkedHashMap<>();
        for (int i=0;i<argNames.length;i++){
            params.put(argNames[i], args[i]);
        }
        long nowTimestamp = System.currentTimeMillis(); //毫秒级时间戳
        Long apiTimeStamp = (Long)params.get("timestamp");
        //大于1分钟则超时
        if (nowTimestamp-apiTimeStamp >60*1000){
            return "timout";
        }
        if (!checkSign(params)){
            return "check failed";
        }
        // 执行方法
        Object result = point.proceed();
        return result;
    }

    private static boolean checkSign(Map<String, Object> params){
        String signStr = params.remove("sign").toString();
        StringBuilder paramsBuilder = new StringBuilder();
        params.forEach((k,v)->{
            paramsBuilder.append(k)
                    .append(v.toString());
        });
        byte[] data = paramsBuilder.toString().getBytes();
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, privateKey, publicKey);
        byte[] signed = sign.sign(data);//签名
        String signedStr = Base64.encode(signed);
        return signedStr.equals(signStr);
    }
}
