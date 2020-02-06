package com.changgou.order.service;

import java.util.Map;

/**
 * Created by zps on 2020/1/31 13:39
 */
public interface CartService {

    //添加购物车
    public void addCart(String skuId,Integer num,String username);

    //查询购物车
    public Map list(String username);
}
