package com.pad.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pad.entity.CompanyInfo;
import com.pad.entity.Message;
import com.pad.service.MessageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 留言表 前端控制器
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    @ApiOperation("添加留言")
    @PostMapping("/add")
    @ResponseBody
    public boolean addMessage(
            @ApiParam(name = "context",value = "留言内容",required = true)String context,
            HttpSession session
    ){
        //添加留言
        CompanyInfo user =(CompanyInfo) session.getAttribute("user");
        Message message = new Message();
        message.setCNo(user.getCNo());
        message.setContext(context);
        return messageService.save(message);
    }

}

