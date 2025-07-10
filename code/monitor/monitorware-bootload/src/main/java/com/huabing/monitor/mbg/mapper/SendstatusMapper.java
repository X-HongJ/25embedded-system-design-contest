package com.huabing.monitor.mbg.mapper;

import com.huabing.monitor.mbg.model.Sendstatus;
import com.huabing.monitor.mbg.model.SendstatusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SendstatusMapper {
    long countByExample(SendstatusExample example);

    int deleteByExample(SendstatusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sendstatus record);

    int insertSelective(Sendstatus record);

    List<Sendstatus> selectByExample(SendstatusExample example);

    Sendstatus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sendstatus record, @Param("example") SendstatusExample example);

    int updateByExample(@Param("record") Sendstatus record, @Param("example") SendstatusExample example);

    int updateByPrimaryKeySelective(Sendstatus record);

    int updateByPrimaryKey(Sendstatus record);
}