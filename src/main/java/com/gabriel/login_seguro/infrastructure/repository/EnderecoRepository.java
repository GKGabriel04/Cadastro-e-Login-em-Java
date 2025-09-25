package com.gabriel.login_seguro.infrastructure.repository;

import com.gabriel.login_seguro.infrastructure.entity.EnderecoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EnderecoRepository extends MongoRepository<EnderecoEntity, String> {

    EnderecoEntity findByUsuarioId(String usuarioId);

    @Transactional
    void deleteByUsuarioId(String usuarioId);
}
