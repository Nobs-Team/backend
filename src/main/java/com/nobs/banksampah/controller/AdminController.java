package com.nobs.banksampah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nobs.banksampah.model.Admin;
import com.nobs.banksampah.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Login
    @PostMapping("/login")
    public @ResponseBody String login(@RequestBody Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();

        return adminService.loginAdmin(username, password);
    }

    // Super admin
    @PostMapping("/addAdmin")
    public @ResponseBody String addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }
}
