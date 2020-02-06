package com.changgou.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zps on 2020/2/3 18:29
 */
@Table(name = "tb_seckill_timeslot")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillTimeSlot {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "startTime")
    private Date startTime;

    @Column(name = "endTime")
    private Date endTime;

    @Column(name = "isEnable")
    private String isEnable;

}
