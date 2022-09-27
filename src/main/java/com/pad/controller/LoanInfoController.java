package com.pad.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.pad.entity.CompanyInfo;
import com.pad.entity.LoanInfo;
import com.pad.entity.Message;
import com.pad.service.LoanInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
        System.out.println(1111111);
        return loanInfoService.save(loanInfo);
    }
}

