package com.changgou.goods.controller;

import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Goods;
import com.changgou.goods.service.SpuService;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/spu")
public class SpuController {


    @Autowired
    private SpuService spuService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Spu> spuList = spuService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", spuList);
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        Goods goods = spuService.findGoodsById(id);
        return new Result(true, StatusCode.OK, "查询成功", goods);
    }

    @GetMapping("/findSpuById/{id}")
    public Result<Spu> findSpuById(@PathVariable String id) {
        Spu spu = spuService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", spu);
    }


    /***
     * 新增数据
     * @param goods
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Goods goods) {
        spuService.add(goods);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /***
     * 修改数据
     * @param goods
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Goods goods) {
        spuService.update(goods);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /***
     * 根据ID删除品牌数据 (逻辑删除)
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        spuService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 真正删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/realDel/{id}")
    public Result realDel(@PathVariable("id") String id) {
        spuService.realDel(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Spu> list = spuService.findList(searchMap);
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
        Page<Spu> pageList = spuService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 商品审核与自动上架
     *
     * @param id
     * @return
     */
    @PutMapping(value = "/audit/{id}")
    public Result audit(@PathVariable("id") String id) {
        spuService.audit(id);
        return new Result(true, StatusCode.OK, "商品审核成功");
    }

    /**
     * 商品下架
     *
     * @param id
     * @return
     */
    @PutMapping(value = "/pull/{id}")
    public Result pull(@PathVariable("id") String id) {
        spuService.pull(id);
        return new Result(true, StatusCode.OK, "商品下架成功");
    }

    /**
     * 商品上架
     *
     * @param id
     * @return
     */
    @PutMapping(value = "/put/{id}")
    public Result put(@PathVariable("id") String id) {
        spuService.put(id);
        return new Result(true, StatusCode.OK, "商品上架成功");
    }

    /**
     * 恢复商品
     *
     * @param id
     * @return
     */
    @PutMapping("/restore/{id}")
    public Result restore(@PathVariable("id") String id) {
        spuService.restore(id);
        return new Result(true, StatusCode.OK, "商品恢复成功");
    }

    //添加商品第一步
    @PostMapping("/add/step1")
    public Result addStep1(@RequestBody Spu spu){
        String id = spuService.addStep1(spu);
        return new Result(true,StatusCode.OK,"第一步添加成功",id);
    }
    //添加商品第二步
    @PutMapping("/add/step2")
    public Result addStep2(@RequestBody Spu spu){
        spuService.addStep2(spu);
        return new Result(true,StatusCode.OK,"第二步添加成功");
    }

    //添加商品第三步
    @PutMapping("/add/step3")
    public Result addStep3(@RequestBody Goods goods){
        spuService.addStep3(goods);
        return new Result(true,StatusCode.OK,"第三步添加成功");
    }

    @GetMapping("/findStatus")
    public Result findStatus(){
        Map<String,Long> map = spuService.findStatusNum();
        return new Result(true,StatusCode.OK,"查询成功",map);
    }
}
