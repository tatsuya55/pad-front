package com.pad.controller;


import com.pad.entity.Bank;
import com.pad.entity.Message;
import com.pad.service.BankService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 合作银行表bank 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@RestController
@RequestMapping("/bank")
public class BankController {
  //查询所有银行

    private BankService bankService;

    @ApiOperation("添加留言")
    @PostMapping("/query")
    @ResponseBody
    public List<Bank> queryBank(
            @ApiParam(name = "bankName",value = "银行名",required = true)String bankName

    ){
        return bankService.list(null);
    }
}

