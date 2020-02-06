package com.changgou.web.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.feign.CartFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by zps on 2020/2/1 11:49
 */
@Controller
@RequestMapping("/wcart")
public class CartController {

    @Autowired
    private CartFeign cartFeign;

    @GetMapping("/list")
    public String list(Model model) {
        Map map = cartFeign.list();
        model.addAttribute("items", map);
        return "cart";
    }

    @ResponseBody
    @GetMapping("/add")
    public Result<Map> add(String id, Integer num) {
        cartFeign.add(id, num);
        Map map = cartFeign.list();
        return new Result<>(true, StatusCode.OK, "购物车添加成功", map);
    }
}