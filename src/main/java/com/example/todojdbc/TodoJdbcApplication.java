package com.example.todojdbc;

import com.example.todojdbc.domain.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoJdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoJdbcApplication.class, args);

    }
}