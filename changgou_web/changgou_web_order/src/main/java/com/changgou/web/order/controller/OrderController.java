package com.changgou.web.order.controller;

import com.changgou.entity.Result;
import com.changgou.order.feign.CartFeign;
import com.changgou.order.pojo.OrderItem;
import com.changgou.user.feign.AddressFeign;
import com.changgou.user.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by zps on 2020/2/2 11:51
 */
@Controller
@RequestMapping("/worder")
public class OrderController {


    @Autowired
    private AddressFeign addressFeign;

    @Autowired
    private CartFeign cartFeign;


    @RequestMapping("/ready/order")
    public String readyOrder(Model model) {
        List<Address> list = addressFeign.list().getData();
        model.addAttribute("address", list);

        for (Address address : list) {
            if (address.getIsDefault().equals("1")) {
                model.addAttribute("deAddr", address);
                break;
            }
        }
        Map map = cartFeign.list();
        List<OrderItem> orderItemList = (List<OrderItem>) map.get("orderItemList");
        Integer totalMoney = (Integer) map.get("totalMoney");
        Integer totalNum = (Integer) map.get("totalNum");

        model.addAttribute("carts", orderItemList);
        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("totalNum", totalNum);


        return "order";
    }
}
