package com.univer.lab.deque.impl;

import com.univer.lab.deque.api.DbRepository;
import com.univer.lab.deque.api.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DbRepositoryImpl<ENTITY, ID> implements DbRepository<ENTITY, ID> {

    private final Connection connection;
    private final String tableName;
    private final EntityMapper<ENTITY, ID> entityMapper;

    private static final String SELECT_ALL = "SELECT * FROM ";
    private static final String DELETE_FROM = "DELETE FROM ";
    private static final String WHERE_ID = " WHERE id = ?";

    @Override
    public ENTITY findById(final ID id) {
        String sql = SELECT_ALL + tableName + WHERE_ID;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return entityMapper.fromResultSet(rs);
            }
        } catch (final SQLException ex) {
           log.error("Exception while working with database.",ex);
        }
        return null;
    }

    @Override
    public List<ENTITY> findAll() {
        List<ENTITY> list = new ArrayList<>();
        String sql = SELECT_ALL + tableName;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(entityMapper.fromResultSet(rs));
            }
        } catch (final SQLException ex) {
            log.error("Exception while working with database.",ex);
        }
        return list;
    }

    @Override
    public ENTITY save(final ENTITY entity) {
        try {
            PreparedStatement stmt = entityMapper.createInsertStatement(connection, tableName, entity);
            stmt.executeUpdate();
            return entity;

        } catch (final SQLException ex) {
            log.error("Exception while working with database.",ex);
        }
        return null;
    }

    @Override
    public ENTITY update(final ENTITY entity) {
        try {
            PreparedStatement stmt = entityMapper.createUpdateStatement(connection, tableName, entity);
            stmt.executeUpdate();
            return entity;
        } catch (final SQLException ex) {
            log.error("Exception while working with database.",ex);
        }
        return null;
    }

    @Override
    public void deleteById(final ID id) {
        String sql = DELETE_FROM + tableName + WHERE_ID;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (final SQLException ex) {
            log.error("Exception while working with database.",ex);
        }
    }
}