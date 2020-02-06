package com.changgou.search.controller;


import com.changgou.entity.Page;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * Created by zps on 2020/1/15 13:21
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;


    @RequestMapping("/list")
    public String list(@RequestParam Map<String, String> searchMap, Model model) {

        this.handSearchMap(searchMap);
        Map resultMap = searchService.search(searchMap);
        //封装返回数据
        model.addAttribute("resultMap", resultMap);
        //封装搜索信息
        model.addAttribute("searchMap", searchMap);
        //拼接字符串
        StringBuilder sb = new StringBuilder("/search/list");
        if (searchMap != null && searchMap.size() > 0) {
            sb.append("?");
            for (String key : searchMap.keySet()) {
                if (!key.equals("pageNum") && !key.equals("pageSize") && !key.equals("sortField") && !key.equals("sortRule")) {
                    sb.append(key).append("=").append(searchMap.get(key)).append("&");
                }
            }
        }
        String url = sb.toString().substring(0, sb.length() - 1);
        //封装地址
        model.addAttribute("url", url);

        Page<SkuInfo> page = new Page<SkuInfo>(Long.parseLong(String.valueOf(resultMap.get("total"))), Integer.parseInt(String.valueOf(resultMap.get("pageNum"))), Page.pageSize);

        model.addAttribute("page", page);

        return "search";
    }


    @GetMapping
    @ResponseBody
    public Map search(@RequestParam Map<String, String> map) {
        //处理特殊符号
        this.handSearchMap(map);
        Map search = searchService.search(map);
        return search;
    }

    private void handSearchMap(Map<String, String> map) {
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().startsWith("spec_")) {
                map.put(entry.getKey(), entry.getValue().replace("+", "%2B"));
            }
        }
    }
}
