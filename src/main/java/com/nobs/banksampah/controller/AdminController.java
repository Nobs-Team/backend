package com.nobs.banksampah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nobs.banksampah.model.AdminModel;
import com.nobs.banksampah.repository.AdminRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    // Login
    @PostMapping("/login")
    public @ResponseBody String login(@RequestBody AdminModel admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();

        AdminModel adminMongo = adminRepository.findByUsername(username);

        if (adminMongo != null && adminMongo.getPassword().equals(password)) {
            return "Berhasil login admin";
        } else {
            return "Login gagal";
        }
    }

    // Super admin
    @PostMapping("/addAdmin")
    public @ResponseBody boolean addAdmin(@RequestBody AdminModel admin) {
        adminRepository.insert(admin);
        return true;
    }
}
