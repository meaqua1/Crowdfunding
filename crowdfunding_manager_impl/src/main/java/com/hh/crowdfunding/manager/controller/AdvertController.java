package com.hh.crowdfunding.manager.controller;

import com.github.pagehelper.PageInfo;
import com.hh.crowdfunding.domain.Advertisement;
import com.hh.crowdfunding.domain.User;
import com.hh.crowdfunding.manager.service.AdvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author hh
 * @create 2019-09-19 14:11
 */
@Controller
@RequestMapping("/advert")
public class AdvertController {


    private static final Logger logger = LoggerFactory.getLogger(AdvertController.class);
    @Autowired
    private AdvertService advertService;

    @RequestMapping("/advert")
    public String toIndex() {
        return "advert/advertisement";
    }

    @RequestMapping("/add")
    public String toAdd() {
        return "advert/add";
    }

    @RequestMapping("/findByPages/{page}")
    @ResponseBody
    public PageInfo findByPages(@PathVariable("page") Integer page,
                                @RequestParam(value = "size", defaultValue = "5") Integer size) {
        List<Advertisement> all = advertService.findAllByPages(page, size);
        PageInfo pageInfo = new PageInfo(all);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping("/save")
    public Integer save(Advertisement advertisement, HttpServletRequest request, HttpSession session) {
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;

        MultipartFile mfile = mreq.getFile("advpic");
        if (mfile == null) {
            return 0;
        } else {
            //获得项目名
            String name = mfile.getOriginalFilename();//java.jpg
            //获得后缀
            String extname = name.substring(name.lastIndexOf(".")); // .jpg
            //重命名文件
            String iconpath = UUID.randomUUID().toString() + extname; //  232243343.jpg
            String path = "E:\\JavaWeb\\Crowdfunding\\crowdfunding_main\\src\\main\\resources\\static\\pics\\adv\\" + iconpath;
            logger.info(path);
            try {
                //文件上传
                mfile.transferTo(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            User user = (User) session.getAttribute("user");
            advertisement.setUserid(user.getId());
            advertisement.setStatus("1");
            advertisement.setIconpath(iconpath);
            int count = advertService.insertAdvert(advertisement);
            return 1;
        }
    }
}