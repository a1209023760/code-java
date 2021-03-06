package com.qjzh.idomp.zjc.sc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author keqi
 * @since 2020-02-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictDataVO implements Serializable {

    private static final long serialVersionUID = -1766015948376837558L;

    private Long id;

    private String dictLabel;

    private String dictValue;

    private String remark;

    private String cssClass;

    private String listClass;

    private Integer dictSort;
}
