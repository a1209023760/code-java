package com.keqi.apihu.pj.mapper;

import com.keqi.apihu.pj.domain.PjDatasourceTableDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PjDatasourceTableMapper {
	int deleteByPrimaryKey(Long id);

	int insert(PjDatasourceTableDO record);

	int insertSelective(PjDatasourceTableDO record);

	int insertList(@Param("list") List<PjDatasourceTableDO> list);

	PjDatasourceTableDO selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(PjDatasourceTableDO record);

	int updateByPrimaryKey(PjDatasourceTableDO record);

	void deleteByDatasourceId(Long datasourceId);
}