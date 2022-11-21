package com.example.todojdbc.controller;

import com.example.todojdbc.domain.ToDo;
import com.example.todojdbc.repository.CommonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ToDoController {

    private final CommonRepository<ToDo> repository;
}
