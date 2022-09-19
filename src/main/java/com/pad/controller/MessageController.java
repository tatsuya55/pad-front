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

    @ApiOperation("查询留言")
    @GetMapping("/query")
    public String queryMessage(HttpSession session, Model model){
        CompanyInfo user =(CompanyInfo) session.getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            return "sign-in";
        }
        String cNo = user.getCNo();
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getCNo,cNo);
        List<Message> messageList = messageService.list(wrapper);
        model.addAttribute("messageList",messageList);
        return "project-details";
    }
}

