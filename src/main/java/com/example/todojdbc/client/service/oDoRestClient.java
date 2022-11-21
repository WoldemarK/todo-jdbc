package com.example.todojdbc.client.service;

import com.example.todojdbc.client.ToDoRestClientProperties;
import com.example.todojdbc.domain.ToDo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class oDoRestClient {

    private final RestTemplate restTemplate;
    private final ToDoRestClientProperties properties;

    public Iterable<ToDo> findAll() throws URISyntaxException {
        RequestEntity<Iterable<ToDo>> requestEntity = new RequestEntity<>(HttpMethod.GET,
                new URI(properties.getUrl() + properties.getBasePath()));

        ResponseEntity<Iterable<ToDo>> response = restTemplate.exchange(requestEntity,
                new ParameterizedTypeReference<>() {
                });

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    public ToDo findById(String id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        return restTemplate.getForObject(properties.getUrl() +
                properties.getBasePath() + "/{id}", ToDo.class, params);
    }

    public ToDo upsert(ToDo toDo) throws URISyntaxException {
        RequestEntity<?> requestEntity = new RequestEntity<>(toDo, HttpMethod.
                POST, new URI(properties.getUrl() + properties.getBasePath()));
        ResponseEntity<?> response = restTemplate.exchange(requestEntity, new
                ParameterizedTypeReference<ToDo>() {
                });
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return restTemplate.getForObject(Objects.requireNonNull(response.getHeaders().
                    getLocation()), ToDo.class);
        }
        return null;
    }

    public ToDo setCompleted(String id) throws URISyntaxException {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        restTemplate.postForObject(properties.getUrl() +
                properties.getBasePath() +
                "/{id}?_method=patch", null, ResponseEntity.class, params);
        return findById(id);
    }

    public void delete(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        restTemplate.delete(properties.getUrl() + properties.getBasePath() +
                "/{id}", params);
    }
}