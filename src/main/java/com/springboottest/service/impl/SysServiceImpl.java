package com.springboottest.service.impl;

import com.springboottest.dao.DataDictMapper;
import com.springboottest.dao.SysUserMapper;
import com.springboottest.model.DataDict;
import com.springboottest.service.sys.SysService;
import com.springboottest.service.sys.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ymind on 2018/4/17.
 */
@Service
public class SysServiceImpl  implements SysService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private DataDictMapper dataDictMapper;

    @Override
    public SysUser queryDetail(String id) {
        return sysUserMapper.queryDetail(id);
//        return null;
    }

    @Override
    public DataDict queryDataDictDetail(String id) {
        return dataDictMapper.queryDataDictDetail(id);
    }
}
