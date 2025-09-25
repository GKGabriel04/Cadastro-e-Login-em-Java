package com.gabriel.login_seguro.api.request;

// imports necessários para validação
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UsuarioRequestDTO {

    @NotEmpty(message = "O nome não pode estar em branco")
    private String nome;

    @NotEmpty(message = "O email não pode estar em branco")
    @Email(message = "O formato do email é inválido")
    private String email;

    // CAMPO NOVO E ESSENCIAL
    @NotEmpty(message = "A senha não pode estar em branco")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;

    @NotEmpty(message = "O documento não pode estar em branco")
    private String documento;

    // A anotação @Valid diz ao Spring para validar também os campos DENTRO do EnderecoRequestDTO
    @Valid
    private EnderecoRequestDTO endereco;
}