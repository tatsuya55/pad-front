package com.pad.service.impl;

import com.pad.entity.Address;
import com.pad.mapper.AddressMapper;
import com.pad.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地址表 服务实现类
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    //获取省
    @Override
    public List<Address> getProvince() {
        return baseMapper.getRoot();
    }

    //获取地址
    @Override
    public List<Address> getList(String addressName,String addressRegionId) {
        return baseMapper.getAddressList(addressName,addressRegionId);
    }
}
