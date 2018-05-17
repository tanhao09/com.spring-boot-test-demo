package com.springboottest.service.sys;

import com.springboottest.model.DataDict;

/**
 * Created by ymind on 2018/4/17.
 */
public interface SysService {
    SysUser queryDetail(String id);
    DataDict queryDataDictDetail(String id);
}
