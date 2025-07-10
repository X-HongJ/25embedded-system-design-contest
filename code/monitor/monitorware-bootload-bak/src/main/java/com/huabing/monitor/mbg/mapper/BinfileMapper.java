package com.huabing.monitor.mbg.mapper;

import com.huabing.monitor.mbg.model.Binfile;
import com.huabing.monitor.mbg.model.BinfileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BinfileMapper {
    long countByExample(BinfileExample example);

    int deleteByExample(BinfileExample example);

    int deleteByPrimaryKey(Integer fileId);

    int insert(Binfile record);

    int insertSelective(Binfile record);

    List<Binfile> selectByExample(BinfileExample example);

    Binfile selectByPrimaryKey(Integer fileId);

    int updateByExampleSelective(@Param("record") Binfile record, @Param("example") BinfileExample example);

    int updateByExample(@Param("record") Binfile record, @Param("example") BinfileExample example);

    int updateByPrimaryKeySelective(Binfile record);

    int updateByPrimaryKey(Binfile record);
}