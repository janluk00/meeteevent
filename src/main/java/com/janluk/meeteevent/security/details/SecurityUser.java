package com.janluk.meeteevent.security.details;

import com.janluk.meeteevent.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {

    private final User user;
    private final UUID id;

    public SecurityUser(User user) {
        System.out.println("SecurityUser/SecurityUser");
        this.user = user;
        this.id = user.getId();
    }

    public UUID getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        System.out.println("SecurityUser/getUsername");
        return user.getLogin();
    }

    @Override
    public String getPassword() {
        System.out.println("SecurityUser/getPassword");
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("SecurityUser/getAuthorities");
        return user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
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
