package com.example.todojdbc.repository;

import com.example.todojdbc.domain.ToDo;
import com.example.todojdbc.mapper.ToDoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ToDoRepository implements CommonRepository<ToDo> {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT = "insert into todo " +
            "(id,description, created, modified, completed) " +
            "values (:id,:description,:created,:modified,:completed)";
    private static final String SQL_QUERY_FIND_ALL = "select * from todo";

    private static final String SQL_QUERY_FIND_BY_ID = "select * from todo where id=:id";

    private static final String SQL_UPDATE = "update todo set description =:description," +
            " modified = :modified, completed = :completed where id = :id";

    private static final String SQL_DELETE = "delete from todo where id = :id";


    @Override
    public ToDo save(ToDo toDo) {
        ToDo result = findById(toDo.getId());
        if (result != null) {
            result.setDescription(toDo.getDescription());
            result.setCompleted(toDo.isCompleted());
            result.setModified(LocalDateTime.now());

            return upsert(result, SQL_UPDATE);
        }
        return upsert(toDo, SQL_UPDATE);
    }

    private ToDo upsert(final ToDo toDo, final String sql) {
        Map<String, Object> namedParameters = new HashMap<>();

        namedParameters.put("id", toDo.getId());
        namedParameters.put("description", toDo.getDescription());
        namedParameters.put("created", java.sql.Timestamp.valueOf(toDo.getCreated()));
        namedParameters.put("modified", java.sql.Timestamp.valueOf(toDo.getModified()));
        namedParameters.put("completed", toDo.isCompleted());

        this.jdbcTemplate.update(sql, namedParameters);

        return findById(toDo.getId());
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> toDo) {
        toDo.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo toDo) {
        Map<String, String> namedParameters = Collections.singletonMap("id", toDo.getId());
        this.jdbcTemplate.update(SQL_DELETE, namedParameters);
    }

    @Override
    public ToDo findById(String id) {
        try {
            Map<String, String> namedParameters = Collections.singletonMap("id", id);
            return this.jdbcTemplate.queryForObject(SQL_QUERY_FIND_BY_ID,
                    namedParameters, new ToDoMapper());

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Iterable<ToDo> findAll() {
        return this.jdbcTemplate.query(SQL_QUERY_FIND_ALL, new ToDoMapper());
    }
}
