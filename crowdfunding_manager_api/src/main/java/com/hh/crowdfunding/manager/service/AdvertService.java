package com.hh.crowdfunding.manager.service;

import com.hh.crowdfunding.domain.Advertisement;

import java.util.List;

/**
 * @author hh
 * @create 2019-09-19 14:11
 */
public interface AdvertService {

    List<Advertisement> findAllByPages(Integer page, Integer size);

    int insertAdvert(Advertisement advertisement);


}

