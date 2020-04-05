package com.chauncy.cloud.thread.primary.ch3.cas;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * @Author cheng
 * @create 2020-04-05 13:32
 *
 * 在servlet filter中更新统计指标
 */
@WebFilter("/echo")
public class CountingFilter implements Filter {

    final Indicator indicator = Indicator.getInstance();

    public CountingFilter(){}


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //处理新请求
        indicator.newRequestReceived();
        StatusExposingResponse httpResponse = new StatusExposingResponse(
                (HttpServletResponse) response);

        chain.doFilter(request, httpResponse);

        int statusCode = httpResponse.getStatus();
        if (0 == statusCode || 2 == statusCode / 100) {
            indicator.newRequestProcessed();
        } else {
            indicator.requestProcessedFailed();
        }

    }

    @Override
    public void destroy() {

    }

}

class StatusExposingResponse extends HttpServletResponseWrapper {
    private int httpStatus;

    public StatusExposingResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void sendError(int sc) throws IOException {
        httpStatus = sc;
        super.sendError(sc);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        httpStatus = sc;
        super.sendError(sc, msg);
    }

    @Override
    public void setStatus(int sc) {
        httpStatus = sc;
        super.setStatus(sc);
    }

    @Override
    public int getStatus() {
        return httpStatus;
    }


    public void init(FilterConfig fConfig) throws ServletException {
        // 什么也不做
    }
}
