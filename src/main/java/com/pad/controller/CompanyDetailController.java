package com.pad.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pad.entity.CompanyDetail;
import com.pad.entity.CompanyInfo;
import com.pad.entity.Message;
import com.pad.service.CompanyDetailService;
import com.pad.service.CompanyInfoService;
import com.pad.utils.ImageType;
import com.pad.utils.UploadTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.xml.soap.Detail;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 企业用户详细信息表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Api(tags = "详情页")
@Controller
@RequestMapping("/company-detail")
public class CompanyDetailController {

    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private CompanyDetailService service;

    @ApiOperation("详细信息")
    @GetMapping("/details")
    public String toSignIn(){
        return "companyDetail";
    }

    /**按外键查询
     *
     * @param
     * @return
     */
    @GetMapping("/findDetailByPK")
    @ApiOperation("企业用户详情查询接口")
    public String findByPK(HttpSession session, Model model){
        LambdaQueryWrapper<CompanyDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyDetail::getCNo,"Q1231394-2241");
        CompanyDetail CompanyDetail = service.getOne(wrapper);
        model.addAttribute("Detail",CompanyDetail);
       return "companyDetail";
    }


    /**修改
     *
     * @param companyDetail
     * @return
     */
    @ApiOperation("企业用户详情修改")
    @PutMapping("/update")
    @ResponseBody
    public boolean update(
            @ApiParam(name = "companyDetail" ,value = "详细信息")
            @RequestBody CompanyDetail companyDetail){
        return service.updateById(companyDetail);

    }

    /**
     * 添加详细信息
     * @param companyDetail
     * @return
     */
    @ApiOperation("企业用户详情添加")
    @PostMapping("/save")
    @ResponseBody
    public boolean save(
            @ApiParam(name = "companyDetail" ,value = "详细信息")
            CompanyDetail companyDetail,
            MultipartFile file,
            HttpSession session
    ) throws IOException {
        CompanyInfo user = (CompanyInfo) session.getAttribute("user");
        String cNo = user.getCNo();
        companyDetail.setCNo(cNo);
        String image = UploadTool.uploadImage(ImageType.LICENSE, file);
        companyDetail.setLicense(image);
        boolean save = service.save(companyDetail);
        if (save){
            //添加成功 将认证状态改为认证中
            return companyInfoService.updateAuthStatus(cNo,1);
        }else {
            return false;
        }
    }

}

