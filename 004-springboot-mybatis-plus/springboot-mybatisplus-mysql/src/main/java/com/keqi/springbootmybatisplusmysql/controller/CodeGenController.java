package com.keqi.springbootmybatisplusmysql.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.keqi.springbootmybatisplusmysql.common.AjaxEntity;
import com.keqi.springbootmybatisplusmysql.domain.CodeGenDO;
import com.keqi.springbootmybatisplusmysql.domain.CodeGenVO;
import com.keqi.springbootmybatisplusmysql.mapper.CodeGenMapper;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/code-gen")
@AllArgsConstructor
public class CodeGenController {

	private final CodeGenMapper codeGenMapper;

	/**
	 * 增加
	 *
	 * @param codeGenDO codeGenDO
	 * @return ajaxEntity
	 */
	@PostMapping("/add")
	public AjaxEntity addCodeGen(CodeGenDO codeGenDO) {
		codeGenMapper.insert(
				codeGenDO
						.setCreateTime(LocalDateTime.now())
						.setUpdateTime(LocalDateTime.now())
		);
		return AjaxEntity.success("id", codeGenDO.getId());
	}

	/**
	 * 批量删除
	 *
	 * @param ids ids
	 * @return ajaxEntity
	 */
	@PostMapping("/delete")
	@Transactional
	public AjaxEntity deleteCodeGen(@RequestBody Integer[] ids) {
		codeGenMapper.deleteBatchIds(Arrays.asList(ids));
		return AjaxEntity.success();
	}

	/**
	 * 修改
	 *
	 * @param codeGenDO codeGenDO
	 * @return ajaxEntity
	 */
	@PostMapping("/update")
	public AjaxEntity updateCodeGen(CodeGenDO codeGenDO) {
		codeGenMapper.updateById(codeGenDO);
		return AjaxEntity.success();
	}

	/**
	 * 查询单个
	 *
	 * @param id id
	 * @return ajaxEntity
	 */
	@GetMapping("/get/{id}")
	public AjaxEntity getCodeGen(@PathVariable Long id) {
		CodeGenDO codeGenDO = codeGenMapper.selectById(id);
		CodeGenVO codeGenVO = new CodeGenVO();
		BeanUtil.copyProperties(codeGenDO, codeGenVO);
		return AjaxEntity.success(codeGenVO);
	}

	/**
	 * 查询列表
	 *
	 * @param codeGenVO codeGenVO
	 * @param current   页数
	 * @param size      大小
	 * @return list
	 */
	@PostMapping("/list")
	public AjaxEntity listCodeGen(CodeGenVO codeGenVO, long current, long size) {

		LambdaQueryWrapper<CodeGenDO> lambdaQueryWrapper = new LambdaQueryWrapper<CodeGenDO>()
				.ge(CodeGenDO::getAge, codeGenVO.getAge())
				.orderByDesc(CodeGenDO::getUpdateTime);

		Page<CodeGenDO> codeGenDOPage = codeGenMapper.selectPage(new Page<>(current, size), lambdaQueryWrapper);

		// 如果不希望所有的字段都返回给客户端，是应该在这里遍历结果，然后转成VO对象返回到客户端吗？
		List<CodeGenVO> ret = new ArrayList<>();
		codeGenDOPage.getRecords().forEach(
				x -> {
					CodeGenVO c = new CodeGenVO();
					BeanUtil.copyProperties(x, c);
					ret.add(c);
				}
		);

		return AjaxEntity.list(codeGenDOPage.getTotal(), ret);
	}

}