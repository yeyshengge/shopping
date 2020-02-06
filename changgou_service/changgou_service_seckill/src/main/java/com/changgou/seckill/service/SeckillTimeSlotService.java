package com.changgou.seckill.service;

import com.changgou.seckill.pojo.SeckillTimeSlot;

import java.util.List;

/**
 * Created by zps on 2020/2/3 21:51
 */
public interface SeckillTimeSlotService {

    void openTimePeriod(Integer timeId);

    void deleteTimeSlot(Integer timeId);

    void addTime(SeckillTimeSlot seckillTimeSlot);

    List<SeckillTimeSlot> findActiviAndTimeNum(Integer activityId);

}
