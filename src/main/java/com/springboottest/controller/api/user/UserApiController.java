package com.springboottest.controller.api.user;

import com.springboottest.model.RestResponse;
import com.springboottest.model.User;
import com.springboottest.controller.web.HomeController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ymind on 2018/4/17.
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserApiController extends HomeController {

    @RequestMapping(value = "/getUser")
    public RestResponse getUser(User user){
        System.out.println(user);
        return RestResponse.ok();
    }
}
