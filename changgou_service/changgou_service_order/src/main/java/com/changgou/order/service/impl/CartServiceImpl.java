package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zps on 2020/1/31 13:41
 */
@Service
public class CartServiceImpl implements CartService {


    private static final String CART = "cart_";


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SpuFeign spuFeign;

    @Override
    public void addCart(String skuId, Integer num, String username) {
        //1.查询redis中对应的商品信息
        OrderItem orderItem = (OrderItem) redisTemplate.boundHashOps(CART + username).get(skuId);
        if (orderItem != null) {
            //2.如果存在,则更新数据
            orderItem.setNum(orderItem.getNum() + num);                         //更新数量
            if (orderItem.getNum()<=0){
                //如果数量少于0,则删除该商品信息
                redisTemplate.boundHashOps(CART+username).delete(skuId);
                return;
            }
            orderItem.setMoney(orderItem.getPrice() * orderItem.getNum());      //更新金额
            orderItem.setPayMoney(orderItem.getPrice() * orderItem.getNum());   //实付金额
        } else {
            //3.如果不存在,就插入数据
            Sku sku = skuFeign.findById(skuId).getData();
            Spu spu = spuFeign.findSpuById(sku.getSpuId()).getData();
            //封装数据
            orderItem = this.cartToRedis(sku, spu, num);
        }

        //把orderItem存到redis中

        redisTemplate.boundHashOps(CART + username).put(skuId, orderItem);


    }

    @Override
    public Map list(String username) {
        Map<String, Object> map = new HashMap();

        List<OrderItem> list = redisTemplate.boundHashOps(CART + username).values();

        map.put("orderItemList", list);
        //设置总数量
        Integer totalNum = 0;
        //设置总金额
        Integer totalMoney = 0;
        for (OrderItem orderItem : list) {
            totalNum += orderItem.getNum();
            totalMoney += orderItem.getMoney();
        }

        map.put("totalNum", totalNum);
        map.put("totalMoney", totalMoney);

        return map;

    }

    //封装OrderItem对象
    private OrderItem cartToRedis(Sku sku, Spu spu, Integer num) {
        OrderItem orderItem = new OrderItem();

        orderItem.setSpuId(sku.getSpuId());
        orderItem.setSkuId(sku.getId());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(num * orderItem.getPrice()); //单价*数量
        orderItem.setPayMoney(num * orderItem.getPrice()); //实付金额
        orderItem.setImage(sku.getImage());
        orderItem.setWeight(sku.getWeight() * num); //重量=单个重量*数量
        //分类ID设置
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        return orderItem;
    }
}
