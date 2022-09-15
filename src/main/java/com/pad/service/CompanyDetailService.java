package com.pad.service;

import com.pad.entity.CompanyDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 企业用户详细信息表 服务类
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
public interface CompanyDetailService extends IService<CompanyDetail> {

    /**
     * 外键查询详细信息
     * @param cNo
     * @return
     */
    CompanyDetail selectByFK(String cNo);
}
