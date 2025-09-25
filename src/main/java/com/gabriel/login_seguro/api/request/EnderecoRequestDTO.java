package com.gabriel.login_seguro.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class EnderecoRequestDTO {

    @NotEmpty(message = "A rua não pode estar em branco")
    private String rua;

    @NotNull(message = "O número não pode estar em branco")
    private Long numero;

    @NotEmpty(message = "O bairro não pode estar em branco")
    private String bairro;

    private String complemento; // O complemento é opcional

    @NotEmpty(message = "A cidade não pode estar em branco")
    private String cidade;

    @NotEmpty(message = "O CEP não pode estar em branco")
    private String cep;
}