package com.pad.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pad.entity.CompanyInfo;
import com.pad.entity.CompanyMaterial;
import com.pad.entity.LoanInfo;
import com.pad.service.CompanyInfoService;
import com.pad.service.CompanyMaterialService;
import com.pad.service.LoanInfoService;
import com.pad.utils.ImageType;
import com.pad.utils.UploadTool;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

/**
 * <p>
 * 企业用户材料信息表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Controller
@RequestMapping("/company-material")
public class CompanyMaterialController<file> {

    @Autowired
    private CompanyMaterialService service;

    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private LoanInfoService loanInfoService;


    @ApiOperation("判断材料是否审核")
    @GetMapping("/status")
    @ResponseBody
    public Integer materialStatus(HttpSession session){
        CompanyInfo user =(CompanyInfo) session.getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            return -2;
        }
        String cNo = user.getCNo();
        Integer integer = service.selectByFk(cNo);
        if (integer == null){
            return -3;
        }
        return integer;
    }


    /**
     * 添加材料信息
     * @param companyMaterial
     * @return
     */
    @ApiOperation("企业用户材料添加")
    @PostMapping("/save")
    @ResponseBody
    public boolean save(
            @ApiParam(name = "companyMaterial" ,value = "材料表")
            CompanyMaterial companyMaterial,
            MultipartFile legalImgFile,
            MultipartFile turnoverFile,
            MultipartFile creditFile,
            MultipartFile collateralPhotoFile,
            MultipartFile recordsFile,
            HttpSession session
    ) throws IOException {
        CompanyInfo user= (CompanyInfo) session.getAttribute("user");
        String cNo=user.getCNo();
        companyMaterial.setCNo(cNo);
        String legalImgImage= UploadTool.uploadImage(ImageType.LEGAL_IMG,legalImgFile);
        String turnoverImage=UploadTool.uploadImage(ImageType.TURNOVER,turnoverFile);
        String creditImage=UploadTool.uploadImage(ImageType.CREDIT,creditFile);
        String collateralImage=UploadTool.uploadImage(ImageType.COLLATERAL_PHOTO,collateralPhotoFile);
        String recordsImage=UploadTool.uploadImage(ImageType.RECORDS,recordsFile);
        companyMaterial.setLegalImg(legalImgImage);
        companyMaterial.setTurnover(turnoverImage);
        companyMaterial.setCredit(creditImage);
        companyMaterial.setCollateralPhoto(collateralImage);
        companyMaterial.setRecords(recordsImage);
        companyMaterial.setStatus(0);
        boolean save=service.save(companyMaterial);
        if (save){
            return true;
        }else {
            return false;
        }
    }

    @ApiOperation("企业用户材料修改")
    @PostMapping("/modify")
    @ResponseBody
    public boolean modify(
            @ApiParam(name = "companyMaterial" ,value = "材料表")
                    CompanyMaterial companyMaterial,
            MultipartFile legalImgFile,
            MultipartFile turnoverFile,
            MultipartFile creditFile,
            MultipartFile collateralPhotoFile,
            MultipartFile recordsFile,
            HttpSession session
    ) throws IOException {
        CompanyInfo user= (CompanyInfo) session.getAttribute("user");
        String cNo=user.getCNo();

        LambdaQueryWrapper<CompanyMaterial> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyMaterial::getCNo,cNo);
        wrapper.eq(CompanyMaterial::getStatus,-1);
        wrapper.eq(CompanyMaterial::getIsDeleted,1);
        CompanyMaterial one = service.getOne(wrapper);

        if (!ObjectUtils.isEmpty(legalImgFile)){
            String legalImgImage= UploadTool.uploadImage(ImageType.LEGAL_IMG,legalImgFile);
            one.setLegalImg(legalImgImage);
        }
        if (!ObjectUtils.isEmpty(turnoverFile)){
            String turnoverImage=UploadTool.uploadImage(ImageType.TURNOVER,turnoverFile);
            one.setTurnover(turnoverImage);
        }
        if (!ObjectUtils.isEmpty(creditFile)){
            String creditImage=UploadTool.uploadImage(ImageType.CREDIT,creditFile);
            one.setCredit(creditImage);
        }
        if (!ObjectUtils.isEmpty(collateralPhotoFile)){
            String collateralImage=UploadTool.uploadImage(ImageType.COLLATERAL_PHOTO,collateralPhotoFile);
            one.setCollateralPhoto(collateralImage);
        }
        if (!ObjectUtils.isEmpty(recordsFile)){
            String recordsImage=UploadTool.uploadImage(ImageType.RECORDS,recordsFile);
            one.setRecords(recordsImage);
        }

        one.setLegalPhone(companyMaterial.getLegalPhone());
        one.setLegalName(companyMaterial.getLegalName());
        one.setLegalId(companyMaterial.getLegalId());
        one.setValue(companyMaterial.getValue());
        one.setCollateral(companyMaterial.getCollateral());
        one.setStatus(0);

        //贷款信息改为未审核
        LambdaQueryWrapper<LoanInfo> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(LoanInfo::getCNo,cNo);
        wrapper1.eq(LoanInfo::getIsDeleted,1);
        wrapper1.eq(LoanInfo::getStatus,-1);
        LoanInfo loanInfo = loanInfoService.getOne(wrapper1);
        loanInfo.setStatus(0);
        loanInfoService.updateById(loanInfo);

        return service.updateById(one);
    }
}

