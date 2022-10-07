package com.pad.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.LoanInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 贷款信息表 服务类
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
public interface LoanInfoService extends IService<LoanInfo> {
    /*根据企业编号 查询贷款信息*/
    void findBy(Page<LoanInfo> page, String cNo);
    /*判断贷款申请是否审核*/
    Integer selectStatus(String cNo);
    /*贷款完成*/
    void complete(String id);
}
