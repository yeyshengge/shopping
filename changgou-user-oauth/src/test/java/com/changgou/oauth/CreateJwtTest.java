package com.changgou.oauth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateJwtTest {


    @Test
    public void test01() {
        //指定文件位置
        ClassPathResource classPathResource = new ClassPathResource("changgou.jks");
        //指定秘钥库的密码
        String keyPass = "changgou";
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, keyPass.toCharArray());

        //声明别名
        String alisa = "changgou";
        //声明密码
        String password = "changgou";

        //获取私钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alisa, password.toCharArray());

        //用接口接收
        RSAPrivateKey rsaPrivateKey  = (RSAPrivateKey) keyPair.getPrivate();


        //准备数据
        Map map = new HashMap();
        map.put("name", "zhangsan");
        map.put("age", "18");
        //生成jwt
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(map),new RsaSigner(rsaPrivateKey));


        String encoded = jwt.getEncoded();

        System.out.println(encoded);
    }


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void test02(){
        String changgou = bCryptPasswordEncoder.encode("zhangsan");
        System.out.println(changgou);
    }
}
