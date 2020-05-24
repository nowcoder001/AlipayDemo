package com.alipay.service;

import com.alipay.api.AlipayApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface OrderService {

    /**
     * 支付
     * @param response
     * @param request
     * @throws IOException
     */
    void pay(HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException;

    /**
     * 退款
     * @param response
     * @param session
     * @throws IOException
     */
    void refund(HttpServletResponse response, HttpSession session) throws IOException, AlipayApiException;
}
