package com.pad.service;

import com.pad.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 地址表 服务类
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
public interface AddressService extends IService<Address> {
    //获取省
    List<Address> getProvince();

    //获取地址
    List<Address> getList(String addressName,String addressRegionId);
}
