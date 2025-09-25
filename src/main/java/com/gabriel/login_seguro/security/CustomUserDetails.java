package com.gabriel.login_seguro.security;

import com.gabriel.login_seguro.infrastructure.entity.UsuarioEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final UsuarioEntity usuario;

    public CustomUserDetails(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    // MÉTODO ADICIONADO PARA ACESSAR O NOME DO USUÁRIO
    public String getNome() {
        return this.usuario.getNome();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuario.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
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