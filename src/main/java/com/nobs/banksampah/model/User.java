package com.nobs.banksampah.model;

public class User {

    private String username;
    private String password;
    private String nama;
    private String alamat;
    private String norek;
    private double jumlahpoin;
    private String role;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return this.alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNorek() {
        return this.norek;
    }

    public void setNorek(String norek) {
        this.norek = norek;
    }

    public double getJumlahpoin() {
        return this.jumlahpoin;
    }

    public void setJumlahpoin(double jumlahpoin) {
        this.jumlahpoin = jumlahpoin;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
