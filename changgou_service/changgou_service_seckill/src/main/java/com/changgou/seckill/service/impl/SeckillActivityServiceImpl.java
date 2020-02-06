package com.changgou.seckill.service.impl;

import com.changgou.seckill.dao.SeckillActivityMapper;
import com.changgou.seckill.pojo.SeckillActivity;
import com.changgou.seckill.service.SeckillActivityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by zps on 2020/2/3 22:22
 */
@Service
public class SeckillActivityServiceImpl implements SeckillActivityService {

    @Autowired
    private SeckillActivityMapper seckillActivityMapper;


    @Override
    public void putActivity(SeckillActivity seckillActivity) {
        seckillActivityMapper.insertSelective(seckillActivity);
    }

    @Override
    public void delete(Integer id) {
        seckillActivityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Page<SeckillActivity> findAllActivity(String searhName, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = new Example(SeckillActivity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("title", searhName);
        return (Page<SeckillActivity>) seckillActivityMapper.selectByExample(example);
    }

    @Override
    public void updateMarketable(Integer activityId) {
        SeckillActivity seckillActivity = seckillActivityMapper.selectByPrimaryKey(activityId);
        if ("1".equals(seckillActivity.getIsMarketable())) {
            seckillActivity.setIsMarketable("0");
        } else {
            seckillActivity.setIsMarketable("1");
        }
        seckillActivityMapper.updateByPrimaryKeySelective(seckillActivity);
    }
}
