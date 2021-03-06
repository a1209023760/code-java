package com.keqi.apihu.sys.service.impl;

import com.keqi.apihu.sys.domain.db.UploadFileDO;
import com.keqi.apihu.sys.mapper.UploadFileMapper;
import com.keqi.apihu.sys.service.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UploadFileServiceImpl implements UploadFileService {

    private final UploadFileMapper uploadFileMapper;

    /**
     * 增加
     *
     * @param t t
     */
    @Override
    @Transactional
    public void insert(UploadFileDO t) {
        this.uploadFileMapper.insert(t);
    }

    /**
     * 根据 id 获取对象
     *
     * @param id id
     * @return r
     */
    @Override
    public UploadFileDO getById(Long id) {
        return this.uploadFileMapper.getById(id);
    }

    /**
     * 根据 id 删除文件
     *
     * @param id id
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        this.uploadFileMapper.deleteById(id);
    }
}
