package com.pad.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pad.entity.CompanyDetail;
import com.pad.entity.CompanyInfo;
import com.pad.entity.Message;
import com.pad.entity.CompanyMaterial;
import com.pad.service.CompanyDetailService;
import com.pad.service.CompanyMaterialService;
import com.pad.service.MessageService;
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
    private MessageService messageService;

    @Autowired
    private CompanyDetailService detailService;

    @Autowired
    private CompanyMaterialService companyMaterialService;

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

    @ApiOperation("人脸识别页")
    @GetMapping("/spot")
    public String spot(){
        return "face-recognition";
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

    @GetMapping("/material")
    @ApiOperation("企业用户材料查询接口")
    public String material(HttpSession session, Model model){
        CompanyInfo user = (CompanyInfo) session.getAttribute("user");
        LambdaQueryWrapper<CompanyMaterial> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyMaterial::getCNo,user.getCNo());
        //不显示已经删除的
        wrapper.eq(CompanyMaterial::getIsDeleted,1);
        CompanyMaterial companyMaterial = companyMaterialService.getOne(wrapper);
        if (!ObjectUtils.isEmpty(companyMaterial)){
            model.addAttribute("material",companyMaterial);
        }
        return "companyMaterial";
    }


    @ApiOperation("贷款")
    @GetMapping("/loans")
    public String toLoans(){
        return "loans";
    }

    @ApiOperation("查询留言")
    @GetMapping("/message-details")
    public String queryMessage(HttpSession session, Model model){
        CompanyInfo user =(CompanyInfo) session.getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            return "sign-in";
        }
        String cNo = user.getCNo();
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getCNo,cNo);
        //不显示已经删除的
        wrapper.eq(Message::getIsDeleted,1);
        List<Message> messageList = messageService.list(wrapper);
        model.addAttribute("messageList",messageList);
        return "message-details";
    }
}
