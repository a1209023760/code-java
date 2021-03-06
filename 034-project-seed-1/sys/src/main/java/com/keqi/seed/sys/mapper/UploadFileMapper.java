package com.keqi.seed.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.keqi.seed.sys.domain.db.UploadFileDO;

public interface UploadFileMapper extends BaseMapper<UploadFileDO> {

	UploadFileDO selectById(Long id);

    UploadFileDO selectByName(String name);
}