package br.com.topone.backend.dtos;

import br.com.topone.backend.entities.Role;
import br.com.topone.backend.entities.User;
import br.com.topone.backend.services.validations.UserInsertValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@UserInsertValid
public record UserInsertDTO(
        Long id,
        
        @NotBlank(message = "First name is required")
        @Size(min = 3, max = 160, message = "First name must be between 3 and 160 characters")
        String firstName,
        
        String lastName,
        @NotBlank(message = "Email is required")
        @Size(max = 160, message = "Email must be less than 160 characters")
        
        @Email(message = "Enter a valid email address")
        String email,
        
        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
        String password,
        
        Set<RoleDTO> roles) {

    public UserInsertDTO(User entity) {
        this(entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPassword(),
                new HashSet<>());
    }

    public UserInsertDTO(User entity, Set<Role> roles) {
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