package com.pad.controller;


import com.pad.entity.CompanyInfo;
import com.pad.service.CompanyInfoService;
import com.pad.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 企业用户基本信息表company_info 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Api(tags = "企业用户管理")
@Controller
@RequestMapping("/company")
public class CompanyInfoController {

    @Autowired
    private CompanyInfoService companyInfoService;

    @ApiOperation("企业用户登录")
    @PostMapping("/login")
    @ResponseBody
    public boolean login(
            @ApiParam(name = "cNo",value = "企业统一编号",required = true)String cNo,
            @ApiParam(name = "password",value = "密码",required = true)String password,
            @ApiParam(name = "remember",value = "记住密码",required = true)String remember,
            HttpSession session, HttpServletResponse response
            ){
        //将密码经MD5加密后 与数据库中密码进行比对
        CompanyInfo companyInfo = companyInfoService.login(cNo,MD5.encrypt(password));
        if (ObjectUtils.isEmpty(companyInfo)){
            return false;
        }
        if ("on".equals(remember)){
            //将用户名和密码存入cookie
            Cookie c_password = new Cookie("password", password);
            Cookie c_userNumber = new Cookie("userNumber", cNo);
            Cookie c_remember = new Cookie("remember", remember);
            //设置cookie时间为7天
            c_password.setMaxAge(60 * 60 * 24 * 7);
            c_userNumber.setMaxAge(60 * 60 * 24 * 7);
            c_remember.setMaxAge(60 * 60 * 24 * 7);
            //设置cookie共享路径
            c_password.setPath("/");
            c_userNumber.setPath("/");
            c_remember.setPath("/");
            response.addCookie(c_password);
            response.addCookie(c_userNumber);
            response.addCookie(c_remember);
        }
        session.setAttribute("user",companyInfo);
        return true;
    }

    @ApiOperation("判断用户是否认证")
    @GetMapping("/checkAuthStatus")
    @ResponseBody
    public int checkAuthStatus(HttpSession session){
        CompanyInfo user =(CompanyInfo) session.getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            return -2;//未登录
        }
        return user.getAuthStatus();
    }

    @ApiOperation("修改用户认证状态")
    @GetMapping("/editAuthStatus")
    @ResponseBody
    public boolean editAuthStatus(
            @ApiParam(name = "status",value = "认证状态",required = true)Integer status,
            HttpSession session
    ){
        CompanyInfo user =(CompanyInfo) session.getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            return false;//未登录
        }
        return companyInfoService.updateAuthStatus(user.getCNo(),status);
    }

    @ApiOperation("检查cno是否重复")
    @GetMapping("/checkCno")
    @ResponseBody
    public boolean checkCno(
            @ApiParam(name = "cNo",value = "企业统一编号",required = true)String cNo
    ){
        return companyInfoService.checkCno(cNo);
    }

    @ApiOperation("检查企业名称是否重复")
    @GetMapping("/checkName")
    @ResponseBody
    public boolean checkName(
            @ApiParam(name = "name",value = "企业名称",required = true)String name
    ){
        return companyInfoService.checkName(name);
    }

    @ApiOperation("检查phone是否重复")
    @GetMapping("/checkPhone")
    @ResponseBody
    public boolean checkPhone(
            @ApiParam(name = "phone",value = "企业电话",required = true)String phone
    ){
        return companyInfoService.checkPhone(phone);
    }

    @ApiOperation("检查email是否重复")
    @GetMapping("/checkEmail")
    @ResponseBody
    public boolean checkEmail(
            @ApiParam(name = "email",value = "企业邮箱",required = true)String email
    ){
        return companyInfoService.checkEmail(email);
    }

    @ApiOperation("企业用户注册")
    @PostMapping("/register")
    @ResponseBody
    public boolean register(
            @ApiParam(name = "companyInfo",value = "企业用户注册",required = true)
            CompanyInfo companyInfo
    ){
        //加密密码存入数据库
        String encrypt = MD5.encrypt(companyInfo.getPassword());
        companyInfo.setPassword(encrypt);
        return companyInfoService.save(companyInfo);
    }
}

