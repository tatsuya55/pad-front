package com.pad.service.impl;

import com.pad.entity.CompanyMaterial;
import com.pad.mapper.CompanyMaterialMapper;
import com.pad.service.CompanyMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业用户材料信息表 服务实现类
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Service
public class CompanyMaterialServiceImpl extends ServiceImpl<CompanyMaterialMapper, CompanyMaterial> implements CompanyMaterialService {

    @Autowired
    private CompanyMaterialMapper companyMaterialMapper;


    @Override
    public CompanyMaterial selectByFk(String cNo) {
        return companyMaterialMapper.selectByFk(cNo);
    }
}
