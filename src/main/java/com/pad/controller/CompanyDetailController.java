package com.pad.controller;


import com.pad.entity.CompanyDetail;
import com.pad.service.CompanyDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private CompanyDetailService service;

    @ApiOperation("详细信息")
    @GetMapping("/details")
    public String toSignIn(){
        return "companyDetail";
    }

    /**
     * 添加页面
     * @return
     */
    @GetMapping("/add")
    public String addDetails(){
        return "";
    }

    /**
     * 修改页面
     * @return
     */
    @GetMapping("/revise")
    public String reviseDetails(){
        return "";
    }


    /**按外键查询
     *
     * @param cNo
     * @return
     */
    @GetMapping("/findDetailByPK/{id}")
    @ApiOperation("企业用户详情查询接口")
    @ResponseBody
    public CompanyDetail findByPK(@ApiParam(value = "企业用户外键") @PathVariable("id") String cNo){
        System.out.println(service.selectByFK(cNo));
       return service.selectByFK(cNo);
    }


    /**修改
     *
     * @param companyDetail
     * @return
     */
    @ApiOperation("企业用户详情修改")
    @PutMapping("/update")
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
    public boolean save(
            @ApiParam(name = "companyDetail" ,value = "详细信息")
            @RequestBody CompanyDetail companyDetail){
        return service.save(companyDetail);
    }

}

