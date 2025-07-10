package com.huabing.monitor.mbg.mapper;

import com.huabing.monitor.mbg.model.Ammeter;
import com.huabing.monitor.mbg.model.AmmeterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmmeterMapper {
    long countByExample(AmmeterExample example);

    int deleteByExample(AmmeterExample example);

    int deleteByPrimaryKey(Integer ammeterId);

    int insert(Ammeter record);

    int insertSelective(Ammeter record);

    List<Ammeter> selectByExample(AmmeterExample example);

    Ammeter selectByPrimaryKey(Integer ammeterId);

    int updateByExampleSelective(@Param("record") Ammeter record, @Param("example") AmmeterExample example);

    int updateByExample(@Param("record") Ammeter record, @Param("example") AmmeterExample example);

    int updateByPrimaryKeySelective(Ammeter record);

    int updateByPrimaryKey(Ammeter record);
}