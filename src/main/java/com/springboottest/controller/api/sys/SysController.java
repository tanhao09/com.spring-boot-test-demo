package com.springboottest.controller.api.sys;

import com.springboottest.controller.web.HomeController;
import com.springboottest.model.DataDict;
import com.springboottest.model.RestResponse;
import com.springboottest.service.redis.RedisService;
import com.springboottest.service.sys.SysService;
import com.springboottest.service.sys.SysUser;
import com.springboottest.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ymind on 2018/4/17.
 */
@RestController
@RequestMapping(value = "/api/sys")
public class SysController extends HomeController {
    @Autowired
    private SysService sysService;
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/querySysDetail" ,method = RequestMethod.GET)
    public RestResponse queryDetail(String id){
       SysUser user =  sysService.queryDetail(id);
        redisService.setValue("sys-user", JsonUtil.toJson(user));
        SysUser sysUser = JsonUtil.fromJson(redisService.getValue("sys-user"),SysUser.class);
        return RestResponse.ok(sysUser);
    }

    @RequestMapping(value = "/queryDataDictDetail" ,method = RequestMethod.POST)
    public RestResponse queryDataDictDetail(String id){
        DataDict dataDict =  sysService.queryDataDictDetail(id);
        return RestResponse.ok(dataDict);
    }
}
