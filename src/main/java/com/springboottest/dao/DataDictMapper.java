package com.springboottest.dao;

import com.springboottest.model.DataDict;

/**
 * Created by ymind on 2018/4/17.
 */
public interface DataDictMapper {
    DataDict queryDataDictDetail(String id);
}
