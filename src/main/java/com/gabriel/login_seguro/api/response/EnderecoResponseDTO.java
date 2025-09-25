package com.gabriel.login_seguro.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponseDTO {

    private String rua;
    private Long numero;
    private String bairro;
    private String complemento;
    private String cidade;
    private String cep;

    // MÉTODOS DE MASCARAMENTO

    public String getRuaMascarada() {
        if (rua == null || rua.length() <= 2) {
            return rua; // Retorna original se for muito curto
        }
        // Pega a primeira letra, adiciona '*' e a última letra
        return rua.charAt(0) + "*".repeat(rua.length() - 2) + rua.charAt(rua.length() - 1);
    }

    public String getNumeroMascarado() {
        if (numero == null) {
            return null;
        }
        String numStr = String.valueOf(numero);
        if (numStr.length() <= 1) {
            return numStr;
        }
        // Pega o primeiro dígito e preenche o resto com '*'
        return numStr.charAt(0) + "*".repeat(numStr.length() - 1);
    }
}
