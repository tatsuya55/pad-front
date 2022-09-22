package com.pad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pad.entity.CompanyInfo;
import com.pad.mapper.CompanyInfoMapper;
import com.pad.service.CompanyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 企业用户基本信息表company_info 服务实现类
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Service
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoMapper, CompanyInfo> implements CompanyInfoService {
    //企业用户登录
    @Override
    public CompanyInfo login(String cNo, String encrypt) {
        //通过企业统一编号查询用户
        LambdaQueryWrapper<CompanyInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyInfo::getCNo,cNo);
        CompanyInfo companyInfo = baseMapper.selectOne(wrapper);
        //未查询出用户
        if (ObjectUtils.isEmpty(companyInfo)){
            return null;
        }
        //比较密码是否正确
        if (!encrypt.equals(companyInfo.getPassword())){
            return null;
        }
        return companyInfo;
    }

    //检查cno是否重复
    @Override
    public boolean checkCno(String cNo) {
        LambdaQueryWrapper<CompanyInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyInfo::getCNo,cNo);
        CompanyInfo companyInfo = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(companyInfo)){
            return true;
        }
        return false;
    }

    //检查企业名称是否重复
    @Override
    public boolean checkName(String name) {
        LambdaQueryWrapper<CompanyInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyInfo::getName,name);
        CompanyInfo companyInfo = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(companyInfo)){
            return true;
        }
        return false;
    }

    //检查phone是否重复
    @Override
    public boolean checkPhone(String phone) {
        LambdaQueryWrapper<CompanyInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyInfo::getPhone,phone);
        CompanyInfo companyInfo = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(companyInfo)){
            return true;
        }
        return false;
    }

    //检查email是否重复
    @Override
    public boolean checkEmail(String email) {
        LambdaQueryWrapper<CompanyInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyInfo::getEmail,email);
        CompanyInfo companyInfo = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(companyInfo)){
            return true;
        }
        return false;
    }

    //修改用户认证状态
    @Override
    public boolean updateAuthStatus(String cNo, Integer status) {
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCNo(cNo);
        companyInfo.setAuthStatus(status);
        return baseMapper.updateById(companyInfo)>0;
    }

    //返回用户认证状态
    @Override
    public int getAuthStatus(String cNo) {
        return baseMapper.getAuthStatus(cNo);
    }
}
