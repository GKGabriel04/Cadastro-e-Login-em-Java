package com.gabriel.login_seguro.api;

import com.gabriel.login_seguro.api.request.UsuarioRequestDTO;
import com.gabriel.login_seguro.api.response.UsuarioResponseDTO;
import com.gabriel.login_seguro.business.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<UsuarioResponseDTO> gravaDadosUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return ResponseEntity.ok(usuarioService.registrar(usuarioRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<UsuarioResponseDTO> buscaUsuarioPorEmail(@RequestParam ("email") String email) {
        return ResponseEntity.ok(usuarioService.buscaDadosUsuario(email));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaDadosUsuario(@RequestParam ("email") String email) {
        usuarioService.deletaDadosUsuario(email);
        return ResponseEntity.accepted().build();
    }


}
