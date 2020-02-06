package com.changgou.oauth;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class ParseJwtTest {


    @Test
    public void test01() {

        //基于公钥解析jwt

        //令牌
        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU4MDQ4OTQwOSwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiZ29vZHNfbGlzdCJdLCJqdGkiOiIxNTVmZjczZi1iZTFiLTRmYzAtOGI4OS03NDJlYmNhMWVlYjIiLCJjbGllbnRfaWQiOiJjaGFuZ2dvdSIsInVzZXJuYW1lIjoiaGVpbWEifQ.s2_NN_sK6YCPnd5uZMjkHq7QPiI6Of_6VRFjBlCeXjiZO8Ivz8dDAQ9Bf_o569S3wdbv024dszWdHfzDsaNEiUuEIDlaUYdGfUs5rvRISQjty86Rj8vazSMF2pV7X9wbv2CrcLbqM6oO70gYW1NwoYILuSrNcpYCb0USfcgP90pVmTAAbl83AsybxXwkTTx9FmMpGRWyal_qaf5Fk2tPJqWqfLVS88jh_3STXVvhnmwQjxMKwyneUGXWT0-zrHE_fvLQerEwqOzp2rIE149iB2q5ducrISLFJ4xMOt5yGBHvAdohwpy0kcx3DY0gFv6MuRDCmpQeOedsiQiygrKeEQ";

        //公钥
        String pubKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFsEiaLvij9C1Mz+oyAmt47whAaRkRu/8kePM+X8760UGU0RMwGti6Z9y3LQ0RvK6I0brXmbGB/RsN38PVnhcP8ZfxGUH26kX0RK+tlrxcrG+HkPYOH4XPAL8Q1lu1n9x3tLcIPxq8ZZtuIyKYEmoLKyMsvTviG5flTpDprT25unWgE4md1kthRWXOnfWHATVY7Y/r4obiOL1mS5bEa/iNKotQNnvIAKtjBM4RlIDWMa6dmz+lHtLtqDD2LF1qwoiSIHI75LQZ/CNYaHCfZSxtOydpNKq8eb1/PGiLNolD4La2zf0/1dlcr5mkesV570NxRmU1tFm8Zd3MZlZmyv9QIDAQAB-----END PUBLIC KEY-----";
        //校验Jwt
        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(pubKey));
        String claims = token.getClaims();
        System.out.println(claims);
    }
}
