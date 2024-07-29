package br.com.topone.backend.repositories;

import br.com.topone.backend.entities.Role;
import br.com.topone.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}
