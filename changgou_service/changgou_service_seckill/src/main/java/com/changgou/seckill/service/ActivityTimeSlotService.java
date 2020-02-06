package com.changgou.seckill.service;

/**
 * Created by zps on 2020/2/3 22:37
 */
public interface ActivityTimeSlotService {

    void addAT(Integer activityId, Integer timeId, Long[] skuIds);

    void delAG(Integer activityId, Integer timeId, Long id);

}
