package br.com.topone.backend.repositories;

import br.com.topone.backend.entities.Product;
import br.com.topone.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}
