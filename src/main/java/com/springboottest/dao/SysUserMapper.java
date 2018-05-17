package com.springboottest.dao;

import com.springboottest.service.sys.SysUser;

/**
 * Created by ymind on 2018/4/18.
 */
public interface SysUserMapper {
    SysUser queryDetail(String id);
}
