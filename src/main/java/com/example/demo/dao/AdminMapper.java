package com.example.demo.dao;

import com.example.demo.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    Admin selectByAdminNameAndPwd(@Param("adminName") String adminName,
                                 @Param("passwordHash") String passwordHash);
    Admin selectByAdminId(String adminId);
    int updateByAdminId(Admin admin);
    int insertSelective(Admin admin);
    List<Admin> findAll();
}
