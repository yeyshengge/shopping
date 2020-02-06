package com.changgou.seckill.service;

import com.changgou.seckill.pojo.SeckillGoods;
import com.github.pagehelper.Page;

import java.util.List;

public interface SecKillGoodsService {

    List<SeckillGoods> list(String time);

    //设置商品_商品列表_单个时间段该活动对应的商品列表查询
    List<SeckillGoods> findGoodsByTime(Integer activity_id, Integer timeslot_id);

    //设置商品_商品列表_更新商品秒杀对应的数据，价格、数量、限购数量、排序
    void updateSeckillGoodsById(SeckillGoods seckillGoods);

    Page<SeckillGoods> findPage(String searhName, int page, int size);

}
