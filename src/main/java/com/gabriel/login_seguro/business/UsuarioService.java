package com.gabriel.login_seguro.business;

import com.gabriel.login_seguro.api.converter.UsuarioConverter;
import com.gabriel.login_seguro.api.converter.UsuarioMapper;
import com.gabriel.login_seguro.api.request.EnderecoRequestDTO;
import com.gabriel.login_seguro.api.request.UsuarioRequestDTO;
import com.gabriel.login_seguro.api.response.UsuarioResponseDTO;
import com.gabriel.login_seguro.infrastructure.entity.EnderecoEntity;
import com.gabriel.login_seguro.infrastructure.entity.UsuarioEntity;
import com.gabriel.login_seguro.infrastructure.exceptions.BusinessException;
import com.gabriel.login_seguro.infrastructure.repository.UsuarioRepository;
import com.gabriel.login_seguro.security.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.Assert.notNull;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final UsuarioMapper usuarioMapper;
    private final EnderecoService enderecoService;
    private final PasswordEncoder passwordEncoder;
    private final CryptoUtils cryptoUtils;

    private UsuarioEntity salvaUsuario(UsuarioEntity usuarioEntity) {
        return usuarioRepository.save(usuarioEntity);
    }

    @Transactional
    public UsuarioResponseDTO registrar(UsuarioRequestDTO usuarioRequestDTO) {
        try {
            notNull(usuarioRequestDTO, "Os dados do usuário são obrigatórios");

            if (usuarioRepository.findByEmail(usuarioRequestDTO.getEmail()).isPresent()) {
                throw new BusinessException("Este email já está cadastrado no sistema.");
            }

            UsuarioEntity usuarioEntity = usuarioConverter.paraUsuarioEntity(usuarioRequestDTO);

            usuarioEntity.setPassword(passwordEncoder.encode(usuarioRequestDTO.getPassword()));

            usuarioEntity.setDocumento(cryptoUtils.encrypt(usuarioRequestDTO.getDocumento()));

            usuarioEntity.setRoles(Set.of("ROLE_USER"));
            UsuarioEntity usuarioSalvo = salvaUsuario(usuarioEntity);
            EnderecoEntity enderecoEntity = enderecoService.salvaEndereco(
                    usuarioConverter.paraEnderecoEntity(usuarioRequestDTO.getEndereco(), usuarioSalvo.getId()));
            return usuarioMapper.paraUsuarioResponseDTO(usuarioSalvo, enderecoEntity);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Ocorreu um erro ao tentar registrar o usuário.", e);
        }
    }


    public UsuarioResponseDTO buscaDadosUsuario(String email) {
        UsuarioEntity entity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com o email: " + email));
        EnderecoEntity enderecoEntity = enderecoService.findByUsuarioId(entity.getId());

        UsuarioResponseDTO responseDTO = usuarioMapper.paraUsuarioResponseDTO(entity, enderecoEntity);

        responseDTO.setDocumento(cryptoUtils.decrypt(entity.getDocumento()));

        return responseDTO;
    }

    @Transactional
    public void deletaDadosUsuario(String email) {
        UsuarioEntity entity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado para exclusão."));
        enderecoService.deleteByUsuarioId(entity.getId());
        usuarioRepository.deleteByEmail(email);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioEntity -> {
                    EnderecoEntity endereco = enderecoService.findByUsuarioId(usuarioEntity.getId());
                    UsuarioResponseDTO dto = usuarioMapper.paraUsuarioResponseDTO(usuarioEntity, endereco);

                    dto.setDocumento(cryptoUtils.decrypt(usuarioEntity.getDocumento()));

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void criarAdminSeNaoExistir() {
        String adminEmail = "admin@gmail.com";

        if (usuarioRepository.findByEmail(adminEmail).isEmpty()) {
            System.out.println("Criando usuário administrador padrão...");

            EnderecoRequestDTO enderecoAdmin = EnderecoRequestDTO.builder()
                    .rua("Rua do Administrador")
                    .numero(1L)
                    .bairro("Centro")
                    .cidade("Sistema")
                    .cep("00000-000")
                    .build();

            UsuarioRequestDTO adminDTO = UsuarioRequestDTO.builder()
                    .nome("Administrador do Sistema")
                    .email(adminEmail)
                    .password("testedeadmin")
                    .documento("00000000000")
                    .endereco(enderecoAdmin)
                    .build();

            UsuarioEntity adminEntity = usuarioConverter.paraUsuarioEntity(adminDTO);
            adminEntity.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
            adminEntity.setDocumento(cryptoUtils.encrypt(adminDTO.getDocumento()));
            adminEntity.setRoles(Set.of("ROLE_ADMIN"));

            UsuarioEntity adminSalvo = salvaUsuario(adminEntity);
            enderecoService.salvaEndereco(
                    usuarioConverter.paraEnderecoEntity(adminDTO.getEndereco(), adminSalvo.getId()));

            System.out.println("Usuário administrador criado com sucesso!");
        } else {
            System.out.println("Usuário administrador já existe.");
        }
    }
    /**
     * Deleta um usuário e seu endereço associado com base no ID do usuário.
     * @param id O ID do usuário a ser deletado.
     */
    @Transactional
    public void deletarPorId(String id) {
        // Verifica se o usuário existe antes de tentar deletar para evitar erros
        if (!usuarioRepository.existsById(id)) {
            throw new BusinessException("Usuário com ID " + id + " não encontrado para exclusão.");
        }
        // A ordem é importante: primeiro deleta o endereço, depois o usuário
        enderecoService.deleteByUsuarioId(id);
        usuarioRepository.deleteById(id);
    }
}