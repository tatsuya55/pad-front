package com.pad.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.pad.entity.Address;
import com.pad.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地址表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Api(tags = "地址管理")
@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @ResponseBody
    @ApiOperation("根据areaId获取地址列表")
    @GetMapping("/getCity/{areaId}")
    public Map<String,Object> getAddressByAreaId(
            @ApiParam(name = "areaId",value = "areaId",required = true)
            @PathVariable String areaId
    ){
        HashMap<String, Object> map = new HashMap<>();
        List<Address> addressList = addressService.getList(null, areaId);
        if (addressList == null || addressList.size() <= 0) {
            return null;
        }
        List<Address> childList = addressService.getList(null, addressList.get(0).getAddressAreaId());
        map.put("addressList",addressList);
        map.put("childList",childList);
        return map;
    }
}

