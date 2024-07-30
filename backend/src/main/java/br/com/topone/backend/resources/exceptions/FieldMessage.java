package br.com.topone.backend.resources.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldMessage {
    
    private String fieldName;
    private String message;

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
