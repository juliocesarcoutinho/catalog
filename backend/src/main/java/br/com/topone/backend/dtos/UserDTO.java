package br.com.topone.backend.dtos;

import br.com.topone.backend.entities.Role;
import br.com.topone.backend.entities.User;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record UserDTO(Long id, String firstName,String lastName, String email, Set<RoleDTO> roles) {
    
    public  UserDTO (User entity) {
        this(entity.getId(),
             entity.getFirstName(),
             entity.getLastName(),
             entity.getEmail(),
             new HashSet<>());
    }
    
    public UserDTO (User entity, Set<Role> roles) {
        this(entity);
        roles.forEach(role -> this.roles.add(new RoleDTO(role)));
    }
}
