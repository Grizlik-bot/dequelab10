package com.univer.lab.deque.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper<ENTITY, ID> {

    ENTITY fromResultSet(ResultSet rs) throws SQLException;

    PreparedStatement createInsertStatement(Connection conn, String tableName, ENTITY entity) throws SQLException;

    PreparedStatement createUpdateStatement(Connection conn, String tableName, ENTITY entity) throws SQLException;
}