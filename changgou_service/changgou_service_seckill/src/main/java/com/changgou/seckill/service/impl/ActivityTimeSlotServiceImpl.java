package com.changgou.seckill.service.impl;

import com.changgou.seckill.dao.ActivityTimeSlotMapper;
import com.changgou.seckill.pojo.ActivityTimeSlot;
import com.changgou.seckill.service.ActivityTimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zps on 2020/2/3 22:38
 */
@Service
public class ActivityTimeSlotServiceImpl implements ActivityTimeSlotService {

    @Autowired
    private ActivityTimeSlotMapper activityTimeSlotMapper;

    @Override
    public void addAT(Integer activityId, Integer timeId, Long[] skuIds) {
        for (Long skuId : skuIds) {
            ActivityTimeSlot activityTimeSlot = new ActivityTimeSlot();
            activityTimeSlot.setActivity_id(activityId);
            activityTimeSlot.setTimeslot_id(timeId);
            activityTimeSlot.setGoods_id(skuId);
            activityTimeSlotMapper.insertSelective(activityTimeSlot);
        }
    }

    @Override
    public void delAG(Integer activityId, Integer timeId, Long id) {
        ActivityTimeSlot activityTimeSlot = new ActivityTimeSlot();
        activityTimeSlot.setGoods_id(id);
        activityTimeSlot.setTimeslot_id(timeId);
        activityTimeSlot.setActivity_id(activityId);
        activityTimeSlotMapper.delete(activityTimeSlot);
    }


}
