package com.changgou.web.filter;

import com.changgou.web.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by zps on 2020/1/30 17:40
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final String LOGIN_URL = "http://localhost:8001/api/oauth/toLogin";

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //判断是否为登录请求,如果是就直接放行
        String path = request.getURI().getPath();
        if ("/api/oauth/login".equals(path) || !UrlFilter.hasAuthorize(path)) {
            //是登录请求，直接放行
            return chain.filter(exchange);
        }

        //判断缓存Cookie中是否有jti的值，如果不存在则拒绝本次访问
        String jti = authService.getJtiFromCookie(request);
        if (StringUtils.isEmpty(jti)) {
            //response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //return response.setComplete();
            return this.toLoginPage(exchange, LOGIN_URL);
        }

        //判断缓存Redis中是否有jti的值，如果不存在则拒绝本次访问
        String jwt = authService.getJwtFormRedis(jti);
        if (StringUtils.isEmpty(jwt)) {
            /*response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();*/
            return this.toLoginPage(exchange, LOGIN_URL);
        }

        //对当前请求进行增强,使他携带令牌信息
        request.mutate().header("Authorization", "Bearer " + jwt);

        return chain.filter(exchange);

    }

    private Mono<Void> toLoginPage(ServerWebExchange exchange, String url) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER); //设置为转发
        response.getHeaders().set("Location", url+"?FROM="+exchange.getRequest().getURI().getPath());
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
