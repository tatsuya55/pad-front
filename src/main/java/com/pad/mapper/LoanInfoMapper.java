package com.pad.mapper;

import com.pad.entity.LoanInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 贷款信息表 Mapper 接口
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
public interface LoanInfoMapper extends BaseMapper<LoanInfo> {

    /*根据企业编号 查询贷款信息*/
    List<LoanInfo> findBy(String cNo);
}
