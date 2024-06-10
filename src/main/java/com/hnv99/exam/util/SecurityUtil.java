package com.hnv99.exam.util;

import com.hnv99.exam.security.SysUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Slf4j
public class SecurityUtil {

    private SecurityUtil(){}

    /**
     * Get current user id
     * @return user id
     */
    public static Integer getUserId(){
        SysUserDetails user = (SysUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return user.getUser().getId();
    }

    /**
     * Get current user role
     * @return role
     */
    public static String getRole(){
        List<? extends GrantedAuthority> list = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().toList();
        return list.get(0).toString();
    }

    /**
     * Get the ID of the grade the current user belongs to
     * @return grade ID
     */
    public static Integer getGradeId(){
        SysUserDetails user = (SysUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return user.getUser().getGradeId();
    }
}
