package com.gabriel.login_seguro.api.controller;

import com.gabriel.login_seguro.api.request.UsuarioRequestDTO;
import com.gabriel.login_seguro.api.response.UsuarioResponseDTO;
import com.gabriel.login_seguro.business.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable; // Import adicionado
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Controller responsável por lidar com as requisições de autenticação e
 * cadastro de usuários, servindo as páginas Thymeleaf correspondentes.
 * Usamos @Controller em vez de @RestController porque retornamos nomes de views (HTMLs),
 * e não dados JSON.
 */
@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    // Injeção de dependência do seu serviço via construtor (melhor prática)
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Mapeia a requisição GET para /login e exibe a página de login.
     * @return O nome da view "login" (login.html)
     */
    @GetMapping("/login")
    public String exibirPaginaLogin() {
        return "login";
    }

    /**
     * Mapeia a requisição GET para /cadastro e exibe o formulário de cadastro.
     * @param model O Model do Spring, para adicionar atributos que serão usados na view.
     * @return O nome da view "cadastro" (cadastro.html)
     */
    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioRequestDTO());
        return "cadastro";
    }

    /**
     * Mapeia a requisição POST para /cadastro, processando os dados do formulário.
     * @param usuarioDTO O DTO com os dados do formulário, validado com @Valid.
     * @param bindingResult O resultado da validação. Contém os erros, se houver.
     * @param model O Model, para enviar mensagens de erro de volta para a view.
     * @return Redireciona para o login em caso de sucesso, ou volta para a página de cadastro em caso de erro.
     */
    @PostMapping("/cadastro")
    public String processarCadastro(@Valid @ModelAttribute("usuarioDTO") UsuarioRequestDTO usuarioDTO,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            return "cadastro";
        }

        try {
            usuarioService.registrar(usuarioDTO);
        } catch (RuntimeException e) {
            model.addAttribute("erroCadastro", e.getMessage());
            return "cadastro";
        }

        return "redirect:/login?cadastroSucesso";
    }

    /**
     * Mapeia a requisição GET para a raiz ("/") ou "/home" e exibe a página principal.
     * Esta página só será acessível após o login, conforme configurado no Spring Security.
     * @return O nome da view "home" (home.html)
     */
    @GetMapping({"/", "/home"})
    public String exibirPaginaHome() {
        return "home";
    }

    /**
     * Mapeia a requisição GET para /admin/usuarios e exibe a página de gerenciamento de usuários.
     * Este endpoint já está protegido pela SecurityConfig para ser acessível apenas por admins.
     * @param model O Model, para enviar a lista de usuários para a view.
     * @return O nome da view "admin/usuarios" (admin/usuarios.html)
     */
    @GetMapping("/admin/usuarios")
    public String exibirPaginaAdminUsuarios(Model model) {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuarios";
    }

    /**
     * Mapeia a requisição POST para /admin/usuarios/deletar/{id} para excluir um usuário.
     * @param id O ID do usuário a ser deletado, recebido da URL.
     * @return Redireciona o administrador de volta para a lista de usuários.
     */
    @PostMapping("/admin/usuarios/deletar/{id}")
    public String deletarUsuario(@PathVariable("id") String id) {
        usuarioService.deletarPorId(id);
        // Redireciona de volta para a lista de usuários para que o admin veja o resultado
        return "redirect:/admin/usuarios";
    }
}