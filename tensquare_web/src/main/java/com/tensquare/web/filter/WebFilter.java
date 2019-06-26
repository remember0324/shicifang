package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * web过滤器
 *
 * @Author Rem
 * @Date 2019-06-25
 */

@Component
public class WebFilter extends ZuulFilter {

    /**
     * 配置过滤的时间
     * pre 过滤器
     * post 过滤后
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤优先级 0为优先级最大
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否过滤 true:过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器具体逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.err.println("zull过滤器");
        //获得request对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //获取header中的内容
        String authorization = request.getHeader("Authorization");

        //判断是否有头信息,有继续向下传递
        if (!StringUtils.isEmpty(authorization)) {
            currentContext.addZuulRequestHeader("Authorization", authorization);
        }
        return null;
    }
}
