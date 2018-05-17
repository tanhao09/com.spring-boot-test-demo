package com.springboottest.controller.web.user;

import com.springboottest.controller.web.HomeController;
import com.springboottest.model.Resource;
import com.springboottest.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ymind on 2018/4/12.
 */
@Controller
public class UserController extends HomeController {
    @Autowired
    private Resource resource;



    /**
     * 测试项目是否正常运转
     * @return
     */
    @RequestMapping("/getUser")
    public Object getUser() {
        User user = new User(4, 5, "18671086601", "tanhao", null);
        user.setAvatar("https://www.imooc.com");
        return user;
    }
    /**
     * 测试项目是否正常运转
     * @return
     */
    @RequestMapping("/getUsertest")
    public Object getUsertest() {
        User user = new User(2,3, "17610361570", "linzibin", "");
        return user;
    }

    /**
     * 测试属性配置是否生效
     * @return
     */
    @RequestMapping("/getResource" )
    public Object sendResource(){
        Resource bean = new Resource();
        BeanUtils.copyProperties(resource,bean);
//        System.out.println(JSONObject.);
        return bean;

//        return  resource;
    }

    /**
     * 测试freemaker模板是否生效
     * @param model
     * @return
     */
    @RequestMapping("/ft/getResource" )
    public String  sendFtlResource(Map model){
        Resource bean = new Resource();
        BeanUtils.copyProperties(resource,bean);
        model.put("resource",bean);
        return "/ftl/index";

    }

    /**
     * 测试thyeleaf模板是否生效
     * @param model
     * @return
     */
    @RequestMapping(value = "/th/getResource")
    public String sendThView(Map model){
        Resource bean = new Resource();
        BeanUtils.copyProperties(resource,bean);
        model.put("resource",bean);
        model.put("name","tanhao");

        User u = new User();
        u.setUserName("superadmin");
        u.setDeptId(10);
        u.setMobile("123465");
        u.setBirthday(new Date());
        u.setDesc("<font color='green'><b>hello rongyiju</b></font>");

    model.put("user",u);

        User u1 = new User();
        u1.setDeptId(19);
        u1.setUserName("imooc");
        u1.setMobile("123456");
        u1.setBirthday(new Date());

        User u2 = new User();
        u2.setDeptId(17);
        u2.setUserName("LeeCX");
        u2.setMobile("123456");
        u2.setBirthday(new Date());

        List<User> userList = new ArrayList<>();
        userList.add(u);
        userList.add(u1);
        userList.add(u2);

       model.put("userList", userList);
        return  "/th/test";
    }


    @RequestMapping(value = "/getDetail")
    public String getDetail (Map model){

        int a = 1 / 0;
        return "/th/detail";
    }

}
