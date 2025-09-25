package com.gabriel.login_seguro.infrastructure.repository;

import com.gabriel.login_seguro.infrastructure.entity.UsuarioEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional; // 1. Adicione este import

public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String> {

    // 2. Altere o tipo de retorno para Optional<UsuarioEntity>
    Optional<UsuarioEntity> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
