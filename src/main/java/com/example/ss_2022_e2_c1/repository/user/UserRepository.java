package com.example.ss_2022_e2_c1.repository.user;
import com.example.ss_2022_e2_c1.config.sql.SQLGetter;
import com.example.ss_2022_e2_c1.entities.Authority;
import com.example.ss_2022_e2_c1.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Log4j2
public class UserRepository {
    private final SQLGetter sqlGetter;
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public UserRepository(@Qualifier("sqlGetters") Map<String, SQLGetter> sqlGetters,
                          @Nullable JdbcTemplate jdbcTemplate,
                          @Nullable ObjectMapper objectMapper) {
        this.sqlGetter = sqlGetters.get("UserRepository");
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public Optional<User> findUserByUsername(String username) {
        String sql = sqlGetter.get("FIND_BY_USER_NAME");
        return jdbcTemplate.query(sql, (rs) -> {
            log.info(rs.getStatement());
            if (rs.next()){
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                Set<Authority> authorities = getAuthorities(rs);
                user.setAuthorities(authorities);
                return Optional.of(user);
            }
            return Optional.empty();
        }, username);
    }

    private Set<Authority> getAuthorities(ResultSet rs) throws SQLException {
        Set<Authority> authorities = new HashSet<>();
        try {
            JsonNode jsonNode = objectMapper.readTree(rs.getString("AUTH"));
            for (JsonNode json : jsonNode) {
                Authority authority = objectMapper.convertValue(json, Authority.class);
                authorities.add(authority);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return authorities;
    }
}
