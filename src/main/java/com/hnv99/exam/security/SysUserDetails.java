package com.hnv99.exam.security;


import com.hnv99.exam.model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class SysUserDetails implements UserDetails {
    private List<SimpleGrantedAuthority> permissions;
    private User user;
    private String username;

    public SysUserDetails(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    public void setPermissions(List<SimpleGrantedAuthority> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getPassword() {
        String myPassword=user.getPassword();
        user.setPassword("");
        return myPassword;
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
