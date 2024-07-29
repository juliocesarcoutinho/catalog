package br.com.topone.backend.resources;

import br.com.topone.backend.dtos.ProductDTO;
import br.com.topone.backend.dtos.UserDTO;
import br.com.topone.backend.dtos.UsertInsertDTO;
import br.com.topone.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    //List all users page
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> list = userService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    //List user by id
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        UserDTO dto = userService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    //Inserir usuário
    @PostMapping
    public ResponseEntity<UserDTO> inserir(@RequestBody @Valid UsertInsertDTO dto) {
        UserDTO novoDto = userService.insert(dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(novoDto.id())
                                .toUri())
                .body(novoDto);
    }
    
    //Atualizar usuário
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> atualizar(@PathVariable("id") Long id, 
                                                @RequestBody 
                                                @Valid UsertInsertDTO dto) {
        UserDTO novoDto = userService.update(id, dto);
        return ResponseEntity.ok().body(novoDto);
    }

    //Delete user
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
