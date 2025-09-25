package com.gabriel.login_seguro.config;

import com.gabriel.login_seguro.business.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UsuarioService usuarioService;

    public AdminUserInitializer(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        usuarioService.criarAdminSeNaoExistir();
    }
}
