package com.pad.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.pad.entity.CompanyInfo;
import com.pad.entity.LoanInfo;
import com.pad.entity.Message;
import com.pad.service.LoanInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 贷款信息表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Controller
@RequestMapping("/loan-info")
public class LoanInfoController {

    @Autowired
    private LoanInfoService loanInfoService;

    @ApiOperation("添加贷款申请")
    @PostMapping("/add")
    @ResponseBody
    public Boolean addLoanInfo(
            @ApiParam(name = "loanInfo",value = "添加的贷款信息",required = true)
            LoanInfo loanInfo,
            HttpSession session
    ){
        CompanyInfo user = (CompanyInfo) session.getAttribute("user");
        loanInfo.setCNo(user.getCNo());
        return loanInfoService.save(loanInfo);
    }

    @ApiOperation("修改贷款申请")
    @PostMapping("/modify")
    @ResponseBody
    public Boolean modifyLoanInfo(
            @ApiParam(name = "loanInfo",value = "修改的贷款信息",required = true)
                    LoanInfo loanInfo,
            HttpSession session
    ){
        CompanyInfo user = (CompanyInfo) session.getAttribute("user");
        String cNo = user.getCNo();
        LambdaQueryWrapper<LoanInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoanInfo::getCNo,cNo);
        wrapper.eq(LoanInfo::getIsDeleted,1);
        wrapper.eq(LoanInfo::getStatus,-1);
        LoanInfo one = loanInfoService.getOne(wrapper);
        one.setAmount(loanInfo.getAmount());
        one.setBankNo(loanInfo.getBankNo());
        one.setBankType(loanInfo.getBankType());
        one.setBankNumber(loanInfo.getBankNumber());
        one.setPeriod(loanInfo.getPeriod());
        one.setPurpose(loanInfo.getPurpose());
        one.setReturnMethod(loanInfo.getReturnMethod());
        one.setStatus(0);
        return loanInfoService.updateById(one);
    }

    @ApiOperation("判断贷款申请是否审核")
    @GetMapping("/status")
    @ResponseBody
    public Integer materialStatus(HttpSession session){
        CompanyInfo user =(CompanyInfo) session.getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            return -2;
        }
        String cNo = user.getCNo();
        Integer integer = loanInfoService.selectStatus(cNo);
        if (integer == null){
            return -3;
        }
        return integer;
    }
}

