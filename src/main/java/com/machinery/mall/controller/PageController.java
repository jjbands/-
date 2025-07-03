package com.machinery.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    
    /**
     * 地址管理页面跳转
     * 前端通过localStorage管理用户状态，这里只负责页面跳转
     */
    @RequestMapping("/address_management")
    public String addressManagementPage() {
        return "address_management";
    }
    
    /**
     * 我的账户页面跳转
     */
    @RequestMapping("/myaccount")
    public String myAccountPage() {
        return "myaccount";
    }
    
    /**
     * 首页跳转
     */
    @RequestMapping("/")
    public String indexPage() {
        return "index";
    }



} 