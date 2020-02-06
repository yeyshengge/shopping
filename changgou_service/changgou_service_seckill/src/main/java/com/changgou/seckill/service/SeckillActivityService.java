package com.changgou.seckill.service;

import com.changgou.seckill.pojo.SeckillActivity;
import com.github.pagehelper.Page;

/**
 * Created by zps on 2020/2/3 22:22
 */
public interface SeckillActivityService {

    void putActivity(SeckillActivity seckillActivity);

    void delete(Integer id);

    Page<SeckillActivity> findAllActivity(String searhName, int page, int size);

    void updateMarketable(Integer activityId);


}
