package com.changgou.oauth.service;

import com.changgou.oauth.util.AuthToken;

/**
 * Created by zps on 2020/1/30 13:09
 */
public interface AuthService {

    AuthToken login(String username, String password, String clientId, String clientSecret);

}
