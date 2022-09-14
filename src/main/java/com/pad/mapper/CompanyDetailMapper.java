package com.pad.mapper;

import com.pad.entity.CompanyDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 企业用户详细信息表 Mapper 接口
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Mapper
public interface CompanyDetailMapper extends BaseMapper<CompanyDetail> {

    /**
     * 外键查询详细信息
     * @param cNo
     * @return
     */
    CompanyDetail selectByFK(String cNo);

}
