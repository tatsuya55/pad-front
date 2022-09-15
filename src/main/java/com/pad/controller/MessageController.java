package com.pad.controller;


import com.pad.entity.Bank;
import com.pad.entity.Message;
import com.pad.service.MessageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 留言表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    @ApiOperation("添加留言")
    @PostMapping("/add")
    @ResponseBody
    public boolean addBank(
            @ApiParam(name = "message",value = "添加留言",required = true)String cNo,
            @ApiParam(name = "password",value = "密码",required = true)String context

    ){
        //添加用户
        Message message = new Message();
        message.setCNo(cNo);
        message.setContext(context);
        return messageService.save(message);
    }
}

