package com.keqi.seed.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.keqi.seed.core.pojo.PageVO;
import com.keqi.seed.sys.domain.db.DictItemDO;
import com.keqi.seed.sys.domain.param.DictItemPageParam;
import com.keqi.seed.sys.domain.param.DictItemParam;
import com.keqi.seed.sys.domain.vo.DictItemVO;
import com.keqi.seed.sys.mapper.DictItemMapper;
import com.keqi.seed.sys.service.DictItemService;
import com.keqi.seed.sys.util.DictUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DictItemServiceImpl implements DictItemService {

	@Autowired
	private DictItemMapper dictItemMapper;
	@Autowired
	private DictUtil dictUtil;

	@Override
	@Transactional
	public void insert(DictItemParam param) {
		DictItemDO t = new DictItemDO();
		BeanUtils.copyProperties(param, t);
		this.dictItemMapper.insert(t);

		this.dictUtil.run();
	}

	@Override
	@Transactional
	public void updateById(DictItemParam param) {
		DictItemDO t = new DictItemDO();
		BeanUtils.copyProperties(param, t);
		this.dictItemMapper.updateById(t);

		this.dictUtil.run();
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.dictItemMapper.deleteById(id);

		this.dictUtil.run();
	}

	@Override
	public PageVO<DictItemVO> page(DictItemPageParam param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<DictItemVO> result = this.dictItemMapper.page(param);

		return new PageVO<>(new PageSerializable<>(result).getTotal(), result);
	}

	@Override
	public List<DictItemVO> selectByTypeCode(String typeCode) {
		return this.dictItemMapper.selectByTypeCode(typeCode);
	}
}
