package com.keqi.knife4j.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.keqi.knife4j.sys.domain.db.MenuDO;
import com.keqi.knife4j.sys.domain.vo.MenuVO;

import java.util.List;

public interface MenuMapper extends BaseMapper<MenuDO> {

	List<MenuVO> selectByAccountId(Long accountId);
}