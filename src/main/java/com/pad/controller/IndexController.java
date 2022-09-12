package com.pad.controller;

import com.pad.service.CompanyInfoService;
import com.pad.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Api(tags = "首页")
@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CompanyInfoService companyInfoService;

    @ApiOperation("测试")
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @ApiOperation("企业用户登录")
    @PostMapping("/login")
    @ResponseBody
    public boolean login(
            @ApiParam(name = "cNo",value = "企业统一编号",required = true)String cNo,
            @ApiParam(name = "password",value = "密码",required = true)String password
    ){
        //将密码经MD5加密后 与数据库中密码进行比对
        return companyInfoService.login(cNo, MD5.encrypt(password));
    }

    @ApiOperation("测试")
    @GetMapping("/sign")
    public String indexr(){
        return "sign-in";
    }


}
