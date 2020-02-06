package com.changgou.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by zps on 2020/2/3 18:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_activity_timeslot")
public class ActivityTimeSlot {

    @Column(name = "activity_id")
    private Integer activity_id;

    @Column(name = "timeslot_id")
    private Integer timeslot_id;

    @Column(name = "goods_id")
    private Long goods_id;


}
