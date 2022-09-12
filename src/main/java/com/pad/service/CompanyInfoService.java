package com.pad.service;

import com.pad.entity.CompanyInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 企业用户基本信息表company_info 服务类
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
public interface CompanyInfoService extends IService<CompanyInfo> {
    //企业用户登录
    boolean login(String cNo, String encrypt);
}
