package com.changgou.goods.daoetc;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpuDao {

    long findAllNum();  //查询所有
    long findMarketable(); //已上架数量
    long findWaitStatus(); //待审核数量
    long findUnStatus(); //未通过数量
}
