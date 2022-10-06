package com.pad.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.*;
import com.pad.service.*;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private PeriodizationService periodizationService;

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

  /*  @ApiOperation("人脸注册页")
    @GetMapping("/face")
    private String face(){
        return "face";
    }*/

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

    @ApiOperation("贷款详情分页显示")
    @GetMapping("/loan-detail")
    public String toLoanDetail(HttpSession session, Model model,
             @RequestParam(defaultValue = "1")long current,
             @RequestParam(defaultValue = "2")long size){
        CompanyInfo user = (CompanyInfo) session.getAttribute("user");
        Page<LoanInfo> loanInfoPage = new Page<>(current,size);
        loanInfoService.findBy(loanInfoPage,user.getCNo());
        List<LoanInfo> infoList = loanInfoPage.getRecords();
        model.addAttribute("infoList",infoList);
        model.addAttribute("loanInfoPage",loanInfoPage);
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
        Integer type = loanData.getType();
        LoanData data = null;
        if (1==type){
            //等额本息还款
            data = LoanCalculator.EqualPrincipalandInterestMethod(loanData);
        }
        if (2==type){
            //等额本金还款
            data = LoanCalculator.EqualPrincipalMethod(loanData);
        }
        model.addAttribute("data",data);
        return "loan-data";
    }

    @ApiOperation("贷款申请")
    @GetMapping("/apply-now")
    public String toApplyNow(HttpSession session,Model model){
        //查询所有银行
        List<Bank> bankList = bankService.list(null);
        model.addAttribute("bankList",bankList);

        //回显贷款
        CompanyInfo user = (CompanyInfo) session.getAttribute("user");
        LambdaQueryWrapper<LoanInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoanInfo::getCNo,user.getCNo());
        //不显示已经删除的
        wrapper.eq(LoanInfo::getIsDeleted,1);
        LoanInfo loanInfo = loanInfoService.getOne(wrapper);
        if (!ObjectUtils.isEmpty(loanInfo)){
            model.addAttribute("loanInfo",loanInfo);
        }
        return "apply-now";
    }

    @ApiOperation("银行信息")
    @GetMapping("/faq")
    public String toFaq(
            @RequestParam(defaultValue = "1")long current,
            @RequestParam(defaultValue = "4")long size,
            Model model){
        //查询所有银行
        Page<Bank> bankPage = new Page<>(current,size);
        IPage<Bank> page = bankService.page(bankPage, null);
        model.addAttribute("bankPage",page);
        return "faq";
    }

    @ApiOperation("还款")
    @GetMapping("/repayment/{loanId}")
    public String toRepayment(
            @ApiParam(value = "贷款编号",name = "loanId",required = true)
            @PathVariable String loanId,Model model
    ){
        LoanInfo loanInfo = loanInfoService.getById(loanId);
        //根据贷款编号查询分期表 有信息 则返回查询到的分期列表
        LambdaQueryWrapper<Periodization> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Periodization::getLId,loanId);
        List<Periodization> periodizationList = periodizationService.list(wrapper);
        model.addAttribute("periodizationList",periodizationList);
        model.addAttribute("loanInfo",loanInfo);
        //计算还款总额
        double totalMoney = 0.0;
        //计算利息总额
        double totalInterests = 0.0;
        for (Periodization periodization : periodizationList) {
            totalMoney+=periodization.getCi();
            totalInterests+=periodization.getInterest();
        }
        model.addAttribute("totalMoney",Math.floor(totalMoney));
        model.addAttribute("totalInterests",Math.floor(totalInterests));
        return "repayment";
    }

    @ApiOperation("审核结果")
    @GetMapping("/result")
    public String toResult(){
        return "result";
    }
}
