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
    public boolean login(String cNo, String encrypt) {
        //通过企业统一编号查询用户
        LambdaQueryWrapper<CompanyInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyInfo::getCNo,cNo);
        CompanyInfo companyInfo = baseMapper.selectOne(wrapper);
        //未查询出用户
        if (ObjectUtils.isEmpty(companyInfo)){
            return false;
        }
        //比较密码是否正确
        if (encrypt.equals(companyInfo.getPassword())){
            return true;
        }
        return false;
    }
}
