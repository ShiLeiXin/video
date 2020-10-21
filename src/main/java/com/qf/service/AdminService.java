package com.qf.service;

import com.qf.pojo.Admin;

import java.util.List;

public interface AdminService {
    public List<Admin> findByAdmin(Admin admin);
}
