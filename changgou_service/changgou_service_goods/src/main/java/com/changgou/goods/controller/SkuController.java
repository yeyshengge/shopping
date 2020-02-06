package com.changgou.goods.controller;

import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.service.SkuService;
import com.changgou.goods.pojo.Sku;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/sku")
public class SkuController {


    @Autowired
    private SkuService skuService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Sku> skuList = skuService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", skuList);
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        Sku sku = skuService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", sku);
    }


    /***
     * 新增数据
     * @param sku
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Sku sku) {
        skuService.add(sku);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /***
     * 修改数据
     * @param sku
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Sku sku, @PathVariable String id) {
        sku.setId(id);
        skuService.update(sku);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        skuService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Sku> list = skuService.findList(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result findPage(@RequestParam Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Sku> pageList = skuService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * feign调用的服务，
     * @param spuId
     * @return
     */
    @GetMapping(value = "/spu/{spuId}")
    public List<Sku> findList(@PathVariable("spuId") String spuId) {
        Map<String, Object> map = new HashMap<>();
        //以后可能会传特定值，比如查询审核状态为1的商品，就不会进if
        if (!"all".equals(spuId)) {
            map.put("spuId", spuId);
        }
        //设置只能查询审核过的商品
        map.put("status", "1");
        List<Sku> list = skuService.findList(map);
        return list;
    }

    @PostMapping("/updateSkuList")
    public Result updateSkuList(@RequestBody List<Map> skuList){
        skuService.updateSkuList(skuList);
        return new Result(true,StatusCode.OK,"修改成功");
    }


}