package com.example.todojdbc.controller;

import com.example.todojdbc.domain.ToDo;
import com.example.todojdbc.domain.ToDoBuilder;
import com.example.todojdbc.repository.CommonRepository;
import com.example.todojdbc.validation.ToDoValidationErrorBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ToDoController {

    private final CommonRepository<ToDo> repository;

    @GetMapping("/todo")
    public ResponseEntity<Iterable<ToDo>> getToDos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable String id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable String id) {
        ToDo result = repository.findById(id);
        result.setCompleted(true);
        repository.save(result);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.ok().header
                ("Location", location.toString()).build();
    }

    @RequestMapping(value = "/todo", method = {RequestMethod.POST,
            RequestMethod.PUT})
    public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo toDo,
                                        Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        ToDo result = repository.save(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable String id) {
        repository.delete(ToDoBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }
}
