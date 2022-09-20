package com.pad.controller;


import com.pad.entity.CompanyInfo;
import com.pad.entity.CompanyMaterial;
import com.pad.service.CompanyInfoService;
import com.pad.service.CompanyMaterialService;
import com.pad.utils.ImageType;
import com.pad.utils.UploadTool;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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


    /**
     * 修改材料信息
     * @param companyMaterial
     * @return
     */
    @ApiOperation("企业用户材料修改接口")
    @PutMapping("/update")
    @ResponseBody
    private boolean update(
            @ApiParam(name = "companyMaterial",value = "企业用户材料")
            @RequestBody CompanyMaterial companyMaterial){
        return service.updateById(companyMaterial);
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
        boolean save=service.save(companyMaterial);
        if (save){
            //添加成功 将认证状态改为认证中
            companyInfoService.updateAuthStatus(cNo,1);
            //更新session
            session.setAttribute("user",companyInfoService.getById(cNo));
            return true;
        }else {
            return false;
        }

    }


}

