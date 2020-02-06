package com.changgou.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.config.TokenDecode;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zps on 2020/1/31 13:37
 */
@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartService cartService;
    @Autowired
    private TokenDecode tokenDecode;

    @GetMapping("/add")
    public Result add(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num) {

        //String username = "zhangsan";
        String username = tokenDecode.getUserInfo().get("username");
        cartService.addCart(skuId, num, username);
        return new Result(true, StatusCode.OK, "购物车添加成功");
    }

    @GetMapping("/list")
    public Map list() {
        //String username = "zhangsan";
        String username = tokenDecode.getUserInfo().get("username");
        Map map = cartService.list(username);
        return map;
    }

}
