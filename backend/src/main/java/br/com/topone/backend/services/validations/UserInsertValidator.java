package br.com.topone.backend.services.validations;

import br.com.topone.backend.dtos.UserInsertDTO;
import br.com.topone.backend.repositories.UserRepository;
import br.com.topone.backend.resources.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
    
    private final UserRepository repository;
    
    public UserInsertValidator(UserRepository repository) {
        this.repository = repository;
    }
	
	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		var user = repository.findByEmail(dto.email());                
		if (user != null) {
            list.add(new FieldMessage("email", "Email already exists"));
        }
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}