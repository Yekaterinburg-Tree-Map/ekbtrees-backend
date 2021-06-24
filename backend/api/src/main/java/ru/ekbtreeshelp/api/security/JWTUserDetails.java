package ru.ekbtreeshelp.api.security;

import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class JWTUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Collection<String> roles;

    public static JWTUserDetails fromParsedTokenPayload(Claims parsedPayload) {
        JWTUserDetails userDetails = new JWTUserDetails();
        userDetails.id = Long.valueOf(parsedPayload.get("id", Integer.class));
        userDetails.email = parsedPayload.get("email", String.class);
        userDetails.firstName = parsedPayload.get("firstName", String.class);
        userDetails.lastName = parsedPayload.get("lastName", String.class);
        //noinspection unchecked
        userDetails.roles = parsedPayload.get("roles", Collection.class);
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
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
