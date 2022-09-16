package com.pad.service;

import com.pad.entity.CompanyMaterial;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 企业用户材料信息表 服务类
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
public interface CompanyMaterialService extends IService<CompanyMaterial> {

    /**
     * 外键查询材料信息
     * @param cNo
     * @return
     */
    CompanyMaterial selectByFk(String cNo);
}
