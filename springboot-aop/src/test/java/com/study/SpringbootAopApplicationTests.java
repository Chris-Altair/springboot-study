package com.study;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.study.webapi.WebApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootAopApplicationTests {
    @Autowired
    private WebApiController webApiController;

    @Test
    void contextLoads() {
        webApiController.before("Alice", "123456",1);
    }
    @Test
    void testAround(){
        long timestamp = 1571550900429L;
        String sign =
                "bp1NdQ+d3ANM1rmFdkE7wmgbtoLjGMuotIKzolDgtydyi51YISGRFpm7VJvHaKwyZX1rV3N/StB6eOdk5bmL7RfPfQCfrFQPsHYRnFvNUmu8A5ulAAr5R7S4X1/YWg0y4ZECsnwznpq8a54YhfoSl4CUMJdY0znPzQC4ull7Hwg=";
        webApiController.api("Alice","123456",1, 1571550900429L, sign);
    }
    @Test
    void testSign(){
        byte[] data = "我是一段测试字符串".getBytes();
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
//签名
        byte[] signed = sign.sign(data);
//验证签名
        boolean verify = sign.verify(data, signed);
        RSA rsa = new RSA();

//获得私钥
        rsa.getPrivateKey();
        rsa.getPublicKeyBase64();
    }

}
