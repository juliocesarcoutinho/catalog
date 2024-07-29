package br.com.topone.backend.services;

import br.com.topone.backend.dtos.RoleDTO;
import br.com.topone.backend.dtos.UserDTO;
import br.com.topone.backend.dtos.UsertInsertDTO;
import br.com.topone.backend.entities.Role;
import br.com.topone.backend.entities.User;
import br.com.topone.backend.repositories.RoleRepository;
import br.com.topone.backend.repositories.UserRepository;
import br.com.topone.backend.services.exceptions.DataBaseException;
import br.com.topone.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashSet;

@Service
public class UserService {

    public final UserRepository repository;
    public final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> list = repository.findAll(pageable);
        return list.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Entity not found"));
        return new UserDTO(user);
    }

    //Inserir usuário
    @Transactional
    public UserDTO insert(UsertInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new UserDTO(entity, entity.getRoles());
    }

    //Atualizar usuário
    @Transactional
    public UserDTO update(Long id, UsertInsertDTO dto) {
        User usuario = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        usuario.getRoles().clear();
        copyDtoToEntity(dto, usuario);
        usuario = repository.save(usuario);
        return new UserDTO(usuario, usuario.getRoles());
    }

    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id not Found " + id));
        try {
            repository.delete(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    //Copiar dados do DTO para a entidade
    private void copyDtoToEntity(UsertInsertDTO dto, User entity) {
        BeanUtils.copyProperties(dto, entity, "id", "roles", "password");
        if (entity.getRoles() == null) {
            entity.setRoles(new HashSet<>());
        }
        entity.getRoles().clear();
        for (RoleDTO roleDto : dto.roles()) {
            Role role = roleRepository.findById(roleDto.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleDto.id()));
            entity.getRoles().add(role);
        }
        if (dto.password() != null && !dto.password().isEmpty()) {
            entity.setPassword(passwordEncoder.encode(dto.password()));
        }
    }
}
