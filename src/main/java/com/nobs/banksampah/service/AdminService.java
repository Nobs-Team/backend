package com.nobs.banksampah.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nobs.banksampah.model.Admin;
import com.nobs.banksampah.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login
    public String loginAdmin(String username, String password) {
        Admin adminMongo = adminRepository.findByUsername(username);

        if (adminMongo != null && adminMongo.getPassword().equals(password)) {
            return "Berhasil login";
        } else {
            return "Gagal login";
        }
    }

    public String addAdmin(Admin admin) {
        try {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            adminRepository.save(admin);
            return "Admin berhasil ditambahkan";
        } catch (Exception e) {
            return "Gagal menambahkan admin: " + e.getMessage();
        }
    }
}
