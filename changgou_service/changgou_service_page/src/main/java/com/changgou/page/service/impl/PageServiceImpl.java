package com.changgou.page.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.entity.Result;
import com.changgou.goods.feign.CategoryFeign;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.page.service.PageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zps on 2020/1/26 17:42
 */
@Service
public class PageServiceImpl implements PageService {


    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private CategoryFeign categoryFeign;

    @Autowired
    private SpuFeign spuFeign;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${pagepath}")
    private String pagepath;

    @Override
    public void generateItemPage(String spuId) {
        //获取context对象,用于存放商品详情数据
        Context context = new Context();
        Map<String, Object> itemDate = this.getData(spuId);
        context.setVariables(itemDate);
        //获取商品详情页生成的指定位置
        File file = new File(pagepath);
        //判断商品详情页文件夹是否存在,不存在则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        //定义输出流,进行文件生成
        Writer writer = null;
        try {
            File file1 = new File(pagepath+"/"+spuId+".html");
            writer = new PrintWriter(file1);

            //生成文件
            /**
             1.模板名称
             2.context对象,包含了模板需要的数据
             3.输出流,指定文件输出位置
             */
            templateEngine.process("item", context, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private Map<String, Object> getData(String spuId) {
        Map<String, Object> resultMap = new HashMap<>();
        //获取spu信息
        Spu spu = spuFeign.findSpuById(spuId).getData();
        resultMap.put("spu", spu);
        //获取图片信息
        if (spu != null) {
            if (StringUtils.isNotEmpty(spu.getImages())) {
                resultMap.put("imageList", spu.getImages().split(","));
            }
        }
        //获取分类信息
        Category category1 = categoryFeign.findById(spu.getCategory1Id()).getData();
        Category category2 = categoryFeign.findById(spu.getCategory2Id()).getData();
        Category category3 = categoryFeign.findById(spu.getCategory3Id()).getData();

        resultMap.put("category1", category1);
        resultMap.put("category2", category2);
        resultMap.put("category3", category3);

        //获取sku集合信息
        List<Sku> list = skuFeign.findList(spuId);

        resultMap.put("skuList", list);
        //添加规格信息
        resultMap.put("specificationList", JSON.parseObject(spu.getSpecItems(), Map.class));

        return resultMap;
    }
}
