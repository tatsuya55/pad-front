package com.pad.mapper;

import com.pad.entity.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 地址表 Mapper 接口
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
public interface AddressMapper extends BaseMapper<Address> {
    //获取省
    List<Address> getRoot();
    //获取地址
    List<Address> getAddressList(String addressName,String addressRegionId);
}
