package org.winterframework.mybatis.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.winterframework.mybatis.dao.model.TradeEntity;

@Mapper
public interface TradeEntityMapper {
    int deleteByPrimaryKey(String tradeNo);

    int insert(TradeEntity row);

    int insertSelective(TradeEntity row);

    TradeEntity selectByPrimaryKey(String tradeNo);

    int updateByPrimaryKeySelective(TradeEntity row);

    int updateByPrimaryKey(TradeEntity row);
}