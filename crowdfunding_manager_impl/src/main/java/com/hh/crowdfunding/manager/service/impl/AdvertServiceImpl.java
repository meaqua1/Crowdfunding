package com.hh.crowdfunding.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.crowdfunding.domain.Advertisement;
import com.hh.crowdfunding.manager.dao.AdvertisementMapper;
import com.hh.crowdfunding.manager.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hh
 * @create 2019-09-19 14:11
 */
@Service
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Override
    public List<Advertisement> findAllByPages(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Advertisement> advertisements = advertisementMapper.selectAll();
        return advertisements;
    }

    @Override
    public int insertAdvert(Advertisement advertisement) {
        return advertisementMapper.insert(advertisement);
    }
}
