package com.gabriel.login_seguro.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private String id;
    private String nome;
    private String email;
    private String documento;
    private EnderecoResponseDTO endereco;


    // MÉTODO DE MASCARAMENTO
    /**
     * Retorna uma versão mascarada do documento (CPF/CNPJ) do usuário.
     * Exemplo: "12345678901" se torna "12*******01".
     * @return O documento mascarado.
     */
    public String getDocumentoMascarado() {
        if (documento == null || documento.length() < 4) {
            return " inválido";
        }
        // Pega os 2 primeiros caracteres, preenche o meio com '*' e adiciona os 2 últimos.
        return documento.substring(0, 2) + "*".repeat(documento.length() - 4) + documento.substring(documento.length() - 2);
    }
}