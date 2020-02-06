package com.changgou.oauth.service.impl;

import com.changgou.oauth.service.AuthService;
import com.changgou.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zps on 2020/1/30 13:09
 */
@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${auth.ttl}")
    private Long ttl;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret) {


        ServiceInstance serviceInstance = loadBalancerClient.choose("user-auth");

        URI uri = serviceInstance.getUri();
        //构建路径
        String url = uri + "/oauth/token";

        //设置体信息
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        //设置头信息
        headers.add("Authorization", this.Encoding(clientId, clientSecret));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(body, headers);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        //获取数据
        ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        Map map = exchange.getBody();

        AuthToken authToken = new AuthToken();

        if (map != null && map.size() > 0 && map.get("access_token") != null && map.get("refresh_token") != null && map.get("jti") != null) {
            //封装数据
            authToken.setAccessToken((String) map.get("access_token"));
            authToken.setRefreshToken((String) map.get("refresh_token"));
            authToken.setJti((String) map.get("jti"));
        }

        //保存到redis,键为jti，值为token
        stringRedisTemplate.boundValueOps(authToken.getJti()).set(authToken.getAccessToken(),ttl, TimeUnit.SECONDS);


        return authToken;
    }


    private String Encoding(String clientId, String clientSecret) {
        String value = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(value.getBytes());
        return "Basic " + new String(encode);
    }
}
