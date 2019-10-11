package com.hh.crowdfunding.potal.dao;

import com.hh.crowdfunding.domain.MemberProjectFollow;
import java.util.List;

public interface MemberProjectFollowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberProjectFollow record);

    MemberProjectFollow selectByPrimaryKey(Integer id);

    List<MemberProjectFollow> selectAll();

    int updateByPrimaryKey(MemberProjectFollow record);
}