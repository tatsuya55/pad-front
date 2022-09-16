package com.pad.controller;


import com.pad.entity.CompanyDetail;
import com.pad.entity.CompanyMaterial;
import com.pad.service.CompanyMaterialService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 企业用户材料信息表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@RestController
@RequestMapping("/company-material")
public class CompanyMaterialController {

    @Autowired
    private CompanyMaterialService service;


    /**
     * 材料展示页面
     * @return
     */
    @GetMapping("/material")
    private String material(){
        return "";
    }

    /**
     * 材料上传页面（添加）
     * @return
     */
    @GetMapping("/addMaterial")
    private String add(){
        return "";
    }

    /**
     * 材料修改页面
     * @return
     */
    @GetMapping("/revise")
    private String update(){
        return "";
    }


    /**
     * 外键查询材料信息
     * @param cNo
     * @return
     */
    @ApiOperation("企业用户材料查询接口")
    @GetMapping("/findMaterialByFK/{id}")
    @ResponseBody
    private CompanyMaterial findByFk(
            @ApiParam(value = "企业用户外键")
            @PathVariable("id") String cNo){
        System.out.println(service.selectByFk(cNo));
        return service.selectByFk(cNo);
    }

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
            @ApiParam(name = "companyDetail" ,value = "详细信息")
            @RequestBody CompanyMaterial companyMaterial){
        return service.save(companyMaterial);
    }


}

