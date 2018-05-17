package com.springboottest.exception;

import com.springboottest.model.RestResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ymind on 2018/4/17.
 */
@ControllerAdvice //表明是一个助手类
public class WebExceptionHandler {


    public static final String IMOOC_ERROR_VIEW = "error";
//
//
//    @ExceptionHandler(value = Exception.class)
//    public Object errorHandler(HttpServletRequest request, HttpResponse response, Exception e){
//        e.printStackTrace();
//        ModelAndView m = new ModelAndView();
//        m.addObject("exception",e);
//        m.addObject("url",request.getRequestURL());
//        m.setViewName(IMOOC_ERROR_VIEW);
//        return m;
//    }

    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(HttpServletRequest reqest,
                               HttpServletResponse response, Exception e) throws Exception {

        e.printStackTrace();

        if (isAjax(reqest)) {
            return RestResponse.errorException(e.getMessage());
        } else {
            ModelAndView mav = new ModelAndView();
            mav.addObject("exception", e);
            mav.addObject("url", reqest.getRequestURL());
            mav.setViewName(IMOOC_ERROR_VIEW);
            return mav;
        }
    }

    /**
     *
     * @Title: IMoocExceptionHandler.java
     * @Package com.imooc.exception
     * @Description: 判断是否是ajax请求
     * Copyright: Copyright (c) 2017
     * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY
     *
     * @author leechenxiang
     * @date 2017年12月3日 下午1:40:39
     * @version V1.0
     */
    public static boolean isAjax(HttpServletRequest httpRequest){
        return  (httpRequest.getHeader("X-Requested-With") != null
                && "XMLHttpRequest"
                .equals( httpRequest.getHeader("X-Requested-With").toString()) );
    }
}
