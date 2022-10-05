package com.pad.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pad.entity.CompanyInfo;
import com.pad.entity.LoanInfo;
import com.pad.service.ApprovalRecordService;
import com.pad.service.LoanInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 审批记录表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Controller
@RequestMapping("/approval-record")
public class ApprovalRecordController {

    @Autowired
    private LoanInfoService loanInfoService;

    @Autowired
    private ApprovalRecordService approvalRecordService;

    @ResponseBody
    @ApiOperation("获得审批留言")
    @GetMapping(value = "/getRecordMessage",produces = "text/plain;charset=utf-8")
    public String getRecordMessage(HttpSession session){
        CompanyInfo user = (CompanyInfo) session.getAttribute("user");
        String cNo = user.getCNo();
        LambdaQueryWrapper<LoanInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoanInfo::getCNo,cNo);
        wrapper.eq(LoanInfo::getIsDeleted,1);
        wrapper.eq(LoanInfo::getStatus,-1);
        LoanInfo one = loanInfoService.getOne(wrapper);
        String message = approvalRecordService.getMessage(one.getId());
        System.out.println(message);
        return message;
    }
}

