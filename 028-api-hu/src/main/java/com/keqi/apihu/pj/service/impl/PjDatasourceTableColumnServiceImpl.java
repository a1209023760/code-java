package com.keqi.apihu.pj.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.keqi.apihu.pj.mapper.PjDatasourceTableColumnMapper;
import com.keqi.apihu.pj.service.PjDatasourceTableColumnService;
@Service
public class PjDatasourceTableColumnServiceImpl implements PjDatasourceTableColumnService{

    @Resource
    private PjDatasourceTableColumnMapper pjDatasourceTableColumnMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return pjDatasourceTableColumnMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PjDatasourceTableColumnDO record) {
        return pjDatasourceTableColumnMapper.insert(record);
    }

    @Override
    public int insertSelective(PjDatasourceTableColumnDO record) {
        return pjDatasourceTableColumnMapper.insertSelective(record);
    }

    @Override
    public PjDatasourceTableColumnDO selectByPrimaryKey(Long id) {
        return pjDatasourceTableColumnMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PjDatasourceTableColumnDO record) {
        return pjDatasourceTableColumnMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PjDatasourceTableColumnDO record) {
        return pjDatasourceTableColumnMapper.updateByPrimaryKey(record);
    }

}
