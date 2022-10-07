package com.pad.controller;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pad.alipay.AliPay;
import com.pad.alipay.AliPayConfig;
import com.pad.entity.Overdue;
import com.pad.entity.Periodization;
import com.pad.service.LoanInfoService;
import com.pad.service.OverdueService;
import com.pad.service.PeriodizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// xjlugv6874@sandbox.com
// 9428521.24 - 30 = 9428491.24 + 30 = 9428521.24
@RestController
@RequestMapping("/alipay")
public class AliPayController {

    private static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";

    @Resource
    private AliPayConfig aliPayConfig;

    @Autowired
    private PeriodizationService periodizationService;

    @Autowired
    private OverdueService overdueService;

    @Autowired
    private LoanInfoService loanInfoService;


    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(AliPay aliPay, HttpServletResponse httpResponse) throws Exception {
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);

        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.set("out_trade_no", aliPay.getTraceNo());  // 我们自己生成的订单编号
        bizContent.set("total_amount", aliPay.getTotalAmount()); // 订单的总金额
        bizContent.set("subject", aliPay.getSubject());   // 支付的名称
        bizContent.set("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(bizContent.toString());

        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            //更新分期还款表
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //商户订单号 分期还款编号
            String outTradeNo = params.get("out_trade_no");
            //买家付款时间
            String gmtPayment = params.get("gmt_payment");
            //买家付款金额
            String amount = params.get("buyer_pay_amount");
            //查询outTradeNo对应分期是否逾期
            Periodization byId = periodizationService.getById(outTradeNo);
            Integer overdue = byId.getOverdue();
            String lId = byId.getLId();
            if (overdue == 0){
                //更新逾期表
                LambdaQueryWrapper<Overdue> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Overdue::getRId,outTradeNo);
                Overdue one = overdueService.getOne(wrapper);
                one.setEndTime(new Date());
                overdueService.updateById(one);
            }
            //更新分期表
            Periodization periodization = new Periodization();
            periodization.setId(outTradeNo);
            periodization.setRepaymentTime(sf.parse(gmtPayment));
            periodization.setMoney(Double.parseDouble(amount));
            periodization.setNumber(params.get("trade_no"));
            periodization.setStatus(1);
            periodizationService.updateById(periodization);

            //判断是否全部还完 还完 将贷款和材料删除 设置为已完成
            LambdaQueryWrapper<Periodization> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Periodization::getLId,lId);
            wrapper.eq(Periodization::getStatus,1);
            //贷款id为lId 且已还款的分期
            List<Periodization> periodizationList1 = periodizationService.list(wrapper);
            LambdaQueryWrapper<Periodization> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(Periodization::getLId,lId);
            //贷款id为lId的分期
            List<Periodization> periodizationList = periodizationService.list(wrapper1);
            //比较两个集合的大小
            System.out.println("=================================");
            System.out.println(periodizationList);
            System.out.println(periodizationList.size());
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(periodizationList1);
            System.out.println(periodizationList1.size());
            if (periodizationList.size()==periodizationList1.size()){
                //全部还完
                loanInfoService.complete(lId);
            }

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
            }
        }
        return "success";
    }

}


