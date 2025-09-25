package com.gabriel.login_seguro.business;

import com.gabriel.login_seguro.api.converter.UsuarioConverter;
import com.gabriel.login_seguro.api.converter.UsuarioMapper;
import com.gabriel.login_seguro.api.request.UsuarioRequestDTO;
import com.gabriel.login_seguro.api.response.UsuarioResponseDTO;
import com.gabriel.login_seguro.infrastructure.entity.EnderecoEntity;
import com.gabriel.login_seguro.infrastructure.entity.UsuarioEntity;
import com.gabriel.login_seguro.infrastructure.exceptions.BusinessException;
import com.gabriel.login_seguro.infrastructure.repository.EnderecoRepository;
import com.gabriel.login_seguro.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;


    public EnderecoEntity salvaEndereco(EnderecoEntity enderecoEntity) {
        return enderecoRepository.save(enderecoEntity);
    }

    public EnderecoEntity findByUsuarioId(String usuarioId) {
        return enderecoRepository.findByUsuarioId(usuarioId);
    }

    public void deleteByUsuarioId(String usuarioId) {
        enderecoRepository.deleteByUsuarioId(usuarioId);
    }


}