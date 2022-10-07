package com.pad.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    IPage<LoanInfo> findBy(Page<LoanInfo> page, String cNo);
    /*判断贷款申请是否审核*/
    Integer getStatus(String cNo);
    /*贷款完成*/
    void updateComplete(String id);
}
