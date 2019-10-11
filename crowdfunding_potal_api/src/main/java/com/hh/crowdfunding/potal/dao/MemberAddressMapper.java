package com.hh.crowdfunding.potal.dao;

import com.hh.crowdfunding.domain.MemberAddress;
import java.util.List;

public interface MemberAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberAddress record);

    MemberAddress selectByPrimaryKey(Integer id);

    List<MemberAddress> selectAll();

    int updateByPrimaryKey(MemberAddress record);
}