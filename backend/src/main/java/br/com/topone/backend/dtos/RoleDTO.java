package br.com.topone.backend.dtos;

import br.com.topone.backend.entities.Role;

public record RoleDTO(Long id, String authority) {

    public RoleDTO {
    }
    
    public RoleDTO(Role role) {
        this(
                role.getId(), 
                role.getAuthority()
        );
    }
}
