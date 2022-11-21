package com.example.todojdbc.mapper;

import com.example.todojdbc.domain.ToDo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDoMapper implements RowMapper<ToDo> {


    @Override
    public ToDo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ToDo toDo = new ToDo();

        toDo.setId(rs.getString("id"));
        toDo.setDescription(rs.getString("description"));
        toDo.setModified(rs.getTimestamp("modified").toLocalDateTime());
        toDo.setCreated(rs.getTimestamp("created").toLocalDateTime());
        toDo.setCompleted(rs.getBoolean("completed"));

        return toDo;
    }
}
