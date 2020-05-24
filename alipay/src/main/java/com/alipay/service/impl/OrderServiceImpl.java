package com.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.config.AlipayConfig;
import com.alipay.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 支付
     * @param response
     * @param request
     * @throws AlipayApiException
     * @throws IOException
     */
    @Override
    public void pay(HttpServletResponse response, HttpServletRequest request) throws AlipayApiException, IOException{
        // 设置编码格式
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        // 初始化AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gateway, AlipayConfig.app_id, AlipayConfig.merchant_private_key,
                AlipayConfig.format, AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
        aliPayRequest.setReturnUrl(AlipayConfig.return_url);
        aliPayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，后台可以写一个工具类生成一个订单号，必填
        String order_number = new String("20200524");
        //付款金额，从前台获取，必填
        String total_amount = new String("198");
        //订单名称/标题，可自定义
        String subject = new String("支付宝沙箱测试");
        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + order_number + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = alipayClient.pageExecute(aliPayRequest).getBody();
        //输出
        out.println(result);//以下写自己的订单代码
        logger.info("返回结果={}",result);
    }

    /**
     * 退款
     * @param response
     * @param session
     * @throws IOException
     * @throws AlipayApiException
     */
    @Override
    public void refund(HttpServletResponse response, HttpSession session) throws IOException, AlipayApiException {
        // 设置编码格式
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gateway, AlipayConfig.app_id, AlipayConfig.merchant_private_key,
                AlipayConfig.format, AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        //商户订单号，必填
        String out_trade_no = new String("20200524");
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = new String("198");
        //标识一次退款请求，同一笔交易多次退款需要保证唯一。如需部分退款，则此参数必传；不传该参数则代表全额退款
        String out_request_no = new String(UUID.randomUUID().toString());
        // 字符转义很重要
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"refund_amount\":\""+ refund_amount +"\","
                + "\"out_request_no\":\""+ out_request_no +"\"}");
        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        //输出
        out.println(result);//以下写自己的订单退款代码
        logger.info("返回结果={}",result);
    }
}
