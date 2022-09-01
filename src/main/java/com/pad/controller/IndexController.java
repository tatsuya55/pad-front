package com.pad.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @ApiOperation("测试")
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
