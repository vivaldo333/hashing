package com.ks.hashing.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
@Slf4j
public class PhoneRepository {
    private static final String QUERY = "SELECT PHONE FROM KS.PHONES";

    @Value("${jdbc.row.fetch.size}")
    private int dbFetchSize;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> findAll() {
        List<String> phones = new LinkedList<>();

        jdbcTemplate.setFetchSize(dbFetchSize);
        jdbcTemplate.query(
                QUERY,
                new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet resultSet) throws SQLException {
                        boolean isFirstNotNullRow = resultSet.isFirst() && resultSet.wasNull() == false;
                        while (isFirstNotNullRow || resultSet.next()) {
                            String phone = resultSet.getString("PHONE");
                            //log.debug("phones: {}", phone);
                            if (StringUtils.isNotEmpty(phone)) {
                                phones.add(phone);
                            }
                            isFirstNotNullRow = false;
                        }
                    }
                }
        );

        log.debug("phones fetched count: {}", phones.size());
        return phones;
    }
}
