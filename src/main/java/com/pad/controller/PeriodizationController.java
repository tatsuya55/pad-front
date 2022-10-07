package com.pad.controller;


import com.pad.entity.Periodization;
import com.pad.service.PeriodizationService;
import com.pad.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 分期还款表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Controller
@RequestMapping("/periodization")
public class PeriodizationController {

    @Autowired
    private PeriodizationService periodizationService;

    @ResponseBody
    @ApiOperation("分期还款，判断是否在还款时间内")
    @GetMapping("/isBetween/{pId}")
    public boolean isBetween(
            @PathVariable String pId
    ){
        Periodization periodization = periodizationService.getById(pId);
        Date now = new Date();
        Date end = periodization.getOriginallyTime();
        Calendar ct=Calendar.getInstance();
        ct.setTime(end);
        //前三天可以开始还款
        ct.add(Calendar.DATE, -3);
        Date start = ct.getTime();
        System.out.println("==========="+now);
        System.out.println("==========="+start);
        System.out.println("==========="+end);
        //判断是否在该范围内
        boolean isBetween = DateUtils.isEffectiveDate(now, start, end);
        return isBetween;
    }
}

