package com.changgou.page.listener;

import com.changgou.page.config.RabbitmqConfig;
import com.changgou.page.service.PageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zps on 2020/1/26 18:11
 */
@Component
public class PageListener {

    @Autowired
    private PageService pageService;

    @RabbitListener(queues = RabbitmqConfig.PAGE_CREATE_QUEUE)
    public void receive(String spuId) {
        System.out.println("静态化这个页面：   " + spuId);
        pageService.generateItemPage(spuId);
    }

}
