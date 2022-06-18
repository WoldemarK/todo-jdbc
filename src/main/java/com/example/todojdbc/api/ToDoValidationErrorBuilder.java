package com.example.todojdbc.api;

import com.example.todojdbc.validation.ToDoValidationError;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class ToDoValidationErrorBuilder {

    public static ToDoValidationError fromBindingErrors(Errors errors) {

        ToDoValidationError error = new ToDoValidationError(
                "Validation failed. " + errors.getErrorCount() + "errors(s)");

        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
