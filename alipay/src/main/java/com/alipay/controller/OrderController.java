package com.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("pay")
    public void toPay(HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {
        orderService.pay(response,request);
    }

    @RequestMapping("refund")
    public void toRefund(HttpServletResponse response, HttpSession session) throws IOException, AlipayApiException {
        orderService.refund(response,session);
    }
}
