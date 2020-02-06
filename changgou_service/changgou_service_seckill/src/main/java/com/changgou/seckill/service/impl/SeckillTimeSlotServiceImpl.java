package com.changgou.seckill.service.impl;

import com.changgou.seckill.dao.SeckillTimeSlotMapper;
import com.changgou.seckill.pojo.SeckillTimeSlot;
import com.changgou.seckill.service.SeckillTimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zps on 2020/2/3 21:51
 */
@Service
public class SeckillTimeSlotServiceImpl implements SeckillTimeSlotService {

    @Autowired
    private SeckillTimeSlotMapper seckillTimeSlotMapper;

    @Override
    public void openTimePeriod(Integer timeId) {
        SeckillTimeSlot seckillTimeSlot = new SeckillTimeSlot();
        seckillTimeSlot.setId(timeId);
        SeckillTimeSlot seckillTimeSlot1 = seckillTimeSlotMapper.selectByPrimaryKey(seckillTimeSlot);
        if ("1".equals(seckillTimeSlot1.getIsEnable())) {
            seckillTimeSlot1.setIsEnable("0");
        } else {
            seckillTimeSlot1.setIsEnable("1");
        }
        seckillTimeSlotMapper.updateByPrimaryKeySelective(seckillTimeSlot1);
    }

    @Override
    public void deleteTimeSlot(Integer timeId) {
        SeckillTimeSlot seckillTimeSlot = new SeckillTimeSlot();
        seckillTimeSlot.setId(timeId);
        seckillTimeSlotMapper.deleteByPrimaryKey(seckillTimeSlot);
    }

    @Override
    public void addTime(SeckillTimeSlot seckillTimeSlot) {
        seckillTimeSlotMapper.insertSelective(seckillTimeSlot);
    }

    @Override
    public List<SeckillTimeSlot> findActiviAndTimeNum(Integer activityId) {
        List<SeckillTimeSlot> seckillTimeSlots = seckillTimeSlotMapper.selectAll();
        return seckillTimeSlots;
    }

}
