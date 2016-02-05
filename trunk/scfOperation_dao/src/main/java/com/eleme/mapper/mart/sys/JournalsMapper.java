package com.eleme.mapper.mart.sys;

import com.eleme.domain.mart.sys.Journals;

public interface JournalsMapper {

    int insert(Journals journals);
    
    int insertDetails(Journals journals);
}