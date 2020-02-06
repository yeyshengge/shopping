package com.changgou.seckill.controller;

import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.seckill.pojo.SeckillActivity;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillTimeSlot;
import com.changgou.seckill.service.ActivityTimeSlotService;
import com.changgou.seckill.service.SecKillGoodsService;
import com.changgou.seckill.service.SeckillActivityService;
import com.changgou.seckill.service.SeckillTimeSlotService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seckill")
public class SecKillGoodsController {

    @Autowired
    private SecKillGoodsService secKillGoodsService;

    @Autowired
    private SeckillTimeSlotService seckillTimeSlotService;

    @Autowired
    private SeckillActivityService seckillActivityService;

    @Autowired
    private ActivityTimeSlotService activityTimeSlotService;

    @RequestMapping("/list")
    public Result<List<SeckillGoods>> list(@RequestParam("time") String time) {
        List<SeckillGoods> seckillGoodsList = secKillGoodsService.list(time);
        return new Result<>(true, StatusCode.OK, "查询成功", seckillGoodsList);
    }

    //设置商品_商品列表_单个时间段该活动对应的商品列表查询
    @GetMapping("/findGoodsByTime/{activity_id}/{timeslot_id}")
    public Result findGoodsByTime(@PathVariable("activity_id") Integer activity_id, @PathVariable("timeslot_id") Integer timeslot_id) {
        List<SeckillGoods> list = secKillGoodsService.findGoodsByTime(activity_id, timeslot_id);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    //设置商品_商品列表_更新商品秒杀对应的数据，价格、数量、限购数量、排序
    @PutMapping("/updateSeckillGoodsById")
    public Result updateSeckillGoodsById(@RequestBody SeckillGoods seckillGoods) {
        secKillGoodsService.updateSeckillGoodsById(seckillGoods);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    //设置商品_商品列表_添加秒杀活动跟商品的关联_商品搜索分页查询
    @GetMapping(value = "/findAllGoods/{currentPage}/{pageSize}/{searhName}")
    public Result findPage(@PathVariable("searhName") String searhName, @PathVariable("currentPage") int page, @PathVariable("pageSize") int size) {

        Page<SeckillGoods> pageList = secKillGoodsService.findPage(searhName, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    //时间段启用和关闭
    @PutMapping("/openTimePeriod/{timeId}")
    public Result openTimePeriod(@PathVariable("timeId") Integer timeId) {
        seckillTimeSlotService.openTimePeriod(timeId);
        return new Result(true, StatusCode.OK, "启用成功");
    }

    //删除时间段
    @DeleteMapping("/deleteTimeSlot/{timeId}")
    public Result deleteTimeSlot(@PathVariable("timeId") Integer timeId) {
        seckillTimeSlotService.deleteTimeSlot(timeId);
        return new Result(true, StatusCode.OK, "删除时间段成功");
    }

    //添加活动
    @PostMapping("/putActivity")
    public Result putActivity(@RequestBody SeckillActivity seckillActivity) {
        seckillActivityService.putActivity(seckillActivity);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    //添加秒杀活动跟商品的关联关系_勾选进行关联功能说明
    @PostMapping("/addAT/{activityId}/{timeId}")
    public Result addAT(@PathVariable("activityId") Integer activityId, @PathVariable("timeId") Integer timeId, @RequestParam("skuIds") Long[] skuIds) {
        activityTimeSlotService.addAT(activityId, timeId, skuIds);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    //删除秒杀活动跟商品的关联关系
    @DeleteMapping("/delAG/{activityId}/{timeId}/{id}")
    public Result delAG(@PathVariable("activityId") Integer activityId, @PathVariable("timeId") Integer timeId, @PathVariable("id") Long id) {
        activityTimeSlotService.delAG(activityId, timeId, id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    //删除活动数据
    @DeleteMapping("/activity/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        seckillActivityService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    //添加秒杀时间段
    @PostMapping("/addTime")
    public Result addTime(@RequestBody SeckillTimeSlot seckillTimeSlot) {
        seckillTimeSlotService.addTime(seckillTimeSlot);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    //秒杀活动列表分页查询
    @GetMapping(value = "/findAllActivity/{currentPage}/{pageSize}/{searhName}")
    public Result findAllActivity(@PathVariable("searhName") String searhName, @PathVariable("currentPage") int page, @PathVariable("pageSize") int size) {
        Page<SeckillActivity> pageList = seckillActivityService.findAllActivity(searhName, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    //上下架秒杀活动
    @PutMapping("/activity/updateMarketable/{activityId}")
    public Result updateMarketable(@PathVariable("activityId") Integer activityId){
        seckillActivityService.updateMarketable(activityId);
        return new Result(true, StatusCode.OK, "设置成功");
    }
    //设置商品_该活动对应各个时间段数量列表查询(默认查询所有)
    @GetMapping("/activiAndTime/findActiviAndTimeNum/{activityId")
    public Result findActiviAndTimeNum(@PathVariable("activityId")Integer activityId){
        List<SeckillTimeSlot> list = seckillTimeSlotService.findActiviAndTimeNum(activityId);
        return new Result(true, StatusCode.OK, "查询成功");
    }
}
