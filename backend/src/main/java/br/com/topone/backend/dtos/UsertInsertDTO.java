package br.com.topone.backend.dtos;

import br.com.topone.backend.entities.Role;
import br.com.topone.backend.entities.User;

import java.util.HashSet;
import java.util.Set;

public record UsertInsertDTO(Long id, String firstName,String lastName, String email, String password, Set<RoleDTO> roles) {

    public UsertInsertDTO(User entity) {
        this(entity.getId(),
             entity.getFirstName(),
             entity.getLastName(),
             entity.getEmail(),
             entity.getPassword(),
             new HashSet<>());
    }

    public UsertInsertDTO(User entity, Set<Role> roles) {
        this(entity);
        roles.forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public UserDTO userDTO() {
        return new UserDTO(
                this.id(),
                this.firstName(),
                this.lastName(),
                this.email(),
                this.roles()
        );
    }
}