package com.pad.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pad.entity.*;
import com.pad.service.AddressService;
import com.pad.service.CompanyDetailService;
import com.pad.service.CompanyMaterialService;
import com.pad.service.MessageService;
import com.pad.service.LoanInfoService;
import com.pad.utils.LoanCalculator;
import com.pad.vo.LoanData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @Autowired
    private AddressService addressService;

    @Autowired
    private LoanInfoService loanInfoService;

    @Autowired
    private BankService bankService;

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

    @ApiOperation("人脸注册页")
    @GetMapping("/face")
    private String face(){
        return "face";
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
        String province = "110000";
        String city = "110100";

        if (!ObjectUtils.isEmpty(companyDetail)){
            model.addAttribute("detail",companyDetail);
            province = companyDetail.getProvince();
            city = companyDetail.getCity();
        }
        //返回地址列表
        //获取省
        List<Address> provinceList = addressService.getProvince();
        //获取市
        List<Address> cityList  = addressService.getList(null, province);
        //获取区
        List<Address> areaList  = addressService.getList(null, city);
        //返回页面
        model.addAttribute("provinceList",provinceList);
        model.addAttribute("cityList",cityList);
        model.addAttribute("areaList",areaList);
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

    @ApiOperation("贷款详情")
    @GetMapping("/loan-detail")
    public String toLoanDetail(HttpSession session, Model model){
        CompanyInfo user = (CompanyInfo) session.getAttribute("user");
        List<LoanInfo> list = loanInfoService.findBy(user.getCNo());
        model.addAttribute("list",list);
        return "loan-detail";
    }

    @ApiOperation("贷款计算器")
    @GetMapping("/loan-calculator")
    public String toLoanCalculator(){
        return "loan-calculator";
    }

    @ApiOperation("利率计结果")
    @GetMapping("/loan-data")
    public String toLoanData(LoanData loanData,Model model){
        String type = loanData.getType();
        LoanData data = null;
        if ("de".equals(type)){
            //等额本息还款
            data = LoanCalculator.EqualPrincipalandInterestMethod(loanData);
        }
        if ("dj".equals(type)){
            //等额本金还款
            data = LoanCalculator.EqualPrincipalMethod(loanData);
        }
        model.addAttribute("data",data);
        return "loan-data";
    }

    @ApiOperation("贷款申请")
    @GetMapping("/apply-now")
    public String toApplyNow(Model model){
        //查询所有银行
        List<Bank> bankList = bankService.list(null);
        model.addAttribute("bankList",bankList);
        return "apply-now";
    }

    @ApiOperation("银行信息")
    @GetMapping("/faq")
    public String toFaq(Model model){
        //查询所有银行
        List<Bank> list = bankService.list(null);
        model.addAttribute("bankAllList",list);
        return "faq";
    }
}
