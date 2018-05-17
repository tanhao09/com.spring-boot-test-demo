package com.springboottest.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ymind on 2018/4/17.
 */
@Controller
public class CommonController  extends  HomeController{

    /**
     * 页面路由
     * @param request
     * @return
     */
    @RequestMapping(value = "/**")
    public String getView(HttpServletRequest request){
        String uri=request.getRequestURI();
        return uri;

    }
}
