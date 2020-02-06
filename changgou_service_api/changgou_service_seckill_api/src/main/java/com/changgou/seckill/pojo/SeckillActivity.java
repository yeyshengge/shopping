package com.changgou.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zps on 2020/2/3 20:32
 */
@Table(name = "tb_seckill_activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillActivity {

    @Id
    @Column(name = "id")
    private Integer id;//ID



    private String title;//活动标题
    @Column(name = "startTime")
    private java.util.Date startTime;//开始时间
    @Column(name = "endTime")
    private java.util.Date endTime;//结束时间
    private String status;//状态

    private String image; //图片名称
    private String isMarketable;//活动内容
}
