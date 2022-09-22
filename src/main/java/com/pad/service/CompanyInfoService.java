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
    CompanyInfo login(String cNo, String encrypt);
    //检查cno是否重复
    boolean checkCno(String cNo);
    //检查企业名称是否重复
    boolean checkName(String name);
    //检查phone是否重复
    boolean checkPhone(String phone);
    //检查email是否重复
    boolean checkEmail(String email);
    //修改用户认证状态
    boolean updateAuthStatus(String cNo, Integer status);
    //返回用户认证状态
    int getAuthStatus(String cNo);
}
