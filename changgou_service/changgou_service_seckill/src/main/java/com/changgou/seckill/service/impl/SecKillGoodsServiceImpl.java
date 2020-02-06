package com.changgou.seckill.service.impl;


import com.changgou.seckill.dao.ActivityTimeSlotMapper;
import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.ActivityTimeSlot;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SecKillGoodsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;


import java.util.ArrayList;
import java.util.List;

@Service
public class SecKillGoodsServiceImpl implements SecKillGoodsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ActivityTimeSlotMapper activityTimeSlotMapper;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    public static final String SECKILL_GOODS_KEY = "seckill_goods_";

    @Override
    public List<SeckillGoods> list(String time) {
        List<SeckillGoods> list = redisTemplate.boundHashOps(SECKILL_GOODS_KEY + time).values();
        return list;
    }

    //设置商品_商品列表_单个时间段该活动对应的商品列表查询
    @Override
    public List<SeckillGoods> findGoodsByTime(Integer activity_id, Integer timeslot_id) {
        List<SeckillGoods> seckillGoodsList = new ArrayList<>();
        ActivityTimeSlot activityTimeSlot = new ActivityTimeSlot();
        activityTimeSlot.setActivity_id(activity_id);
        activityTimeSlot.setTimeslot_id(timeslot_id);
        List<ActivityTimeSlot> list = activityTimeSlotMapper.select(activityTimeSlot);
        for (ActivityTimeSlot timeSlot : list) {
            SeckillGoods seckillGoods = new SeckillGoods();
            seckillGoods.setId(timeSlot.getGoods_id());
            SeckillGoods data = seckillGoodsMapper.selectOne(seckillGoods);
            seckillGoodsList.add(data);
        }
        return seckillGoodsList;
    }

    //设置商品_商品列表_更新商品秒杀对应的数据，价格、数量、限购数量、排序
    @Override
    public void updateSeckillGoodsById(SeckillGoods seckillGoods) {
        seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
    }

    //设置商品_商品列表_添加秒杀活动跟商品的关联_商品搜索分页查询
    @Override
    public Page<SeckillGoods> findPage(String searhName, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = new Example(SeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("title", searhName);
        return (Page<SeckillGoods>) seckillGoodsMapper.selectByExample(example);
    }


}
