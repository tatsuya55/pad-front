package com.pad.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pad.entity.CompanyDetail;
import com.pad.entity.CompanyInfo;
import com.pad.entity.LoanInfo;
import com.pad.service.CompanyDetailService;
import com.pad.service.LoanInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Api(tags = "页面跳转")
@Controller
public class IndexController {

    @Autowired
    private CompanyDetailService detailService;

    @Autowired
    private LoanInfoService loanInfoService;

    @ApiOperation("首页跳转")
    @RequestMapping({"/","/index","/index.html"})
    public String toIndex(){
        return "index";
    }

    @ApiOperation("登录页")
    @GetMapping("/signIn")
    public String toSignIn(){
        return "sign-in";
    }

    @ApiOperation("注册页")
    @GetMapping("/signUp")
    public String toSignUp(){
        return "sign-up";
    }

    @ApiOperation("留言页")
    @GetMapping("/contact")
    public String toMessage(){
        return "contact";
    }

    @GetMapping("/details")
    @ApiOperation("企业用户详情查询接口")
    public String findByPK(HttpSession session, Model model){
        CompanyInfo user = (CompanyInfo) session.getAttribute("user");
        LambdaQueryWrapper<CompanyDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyDetail::getCNo,user.getCNo());
        //不显示已经删除的
        wrapper.eq(CompanyDetail::getIsDeleted,1);
        CompanyDetail companyDetail = detailService.getOne(wrapper);
        if (!ObjectUtils.isEmpty(companyDetail)){
            model.addAttribute("detail",companyDetail);
        }
        return "companyDetail";
    }

    @ApiOperation("贷款")
    @GetMapping("/loans")
    public String toLoans(){
        return "loans";
    }

    @ApiOperation("留言详情")
    @GetMapping("/project-details")
    public String toProjectDetails(){
        return "project-details";
    }

    @ApiOperation("贷款详情")
    @GetMapping("/loan-calculator")
    public String toLoanCalculator(HttpSession session, Model model){
        CompanyInfo user = (CompanyInfo) session.getAttribute("user");
        List<LoanInfo> list = loanInfoService.findBy(user.getCNo());
        model.addAttribute("list",list);
        return "loan-calculator";
    }
}
