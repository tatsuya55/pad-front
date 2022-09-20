package com.pad.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pad.entity.CompanyMaterial;

/**
 * <p>
 * 企业用户材料信息表 Mapper 接口
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
public interface CompanyMaterialMapper extends BaseMapper<CompanyMaterial> {

    /**
     * 外键查询材料信息
     * @param cNo
     * @return
     */
    CompanyMaterial selectByFk(String cNo);
}
