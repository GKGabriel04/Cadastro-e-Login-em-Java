package com.gabriel.login_seguro.infrastructure.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "usuario_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {

    @Id
    private String id;
    private String nome;
    private String email;

    private String documento;

    private String password;
    private Set<String> roles;

    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

}