package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * web过滤器
 *
 * @Author Rem
 * @Date 2019-06-25
 */

@Component
public class WebFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

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

        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }
        String url = request.getRequestURL().toString();
        if (url.indexOf("login") > 0) {
            System.out.println("登陆页面" + url);
            return null;
        }


        //获取header中的内容
        String header = request.getHeader("Authorization");

        //判断是否有头信息,获取内容
        if (!StringUtils.isEmpty(header) && header.startsWith("Bearer ")) {
            //提取token
            String token = header.substring(7);

            try {
                Claims claims = jwtUtil.parseJWT(token);
                if (claims != null) {
                    if ("admin".equals(claims.get("roles"))) {
                        //验证有登陆信息,向下传递
                        currentContext.addZuulRequestHeader("Authorization", header);
                        System.out.println("token验证通过,添加了头信息Authorization");
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //终止运行
                currentContext.setSendZuulResponse(false);
            }
        }
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(403);
        currentContext.setResponseBody("无权访问");
        currentContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
