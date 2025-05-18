package com.univer.lab.deque;

import com.univer.lab.deque.api.EntityMapper;
import com.univer.lab.deque.impl.DbRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DbRepositoryImplTest {

    private Connection connection;
    private EntityMapper<TestEntity, Integer> entityMapper;
    private DbRepositoryImpl<TestEntity, Integer> repository;
    private final String tableName = "test_table";

    @BeforeEach
    void setUp() {
        connection = mock(Connection.class);
        entityMapper = mock(EntityMapper.class);
        repository = new DbRepositoryImpl<>(connection, tableName, entityMapper);
    }

    @Test
    void testFindByIdReturnsEntity() throws Exception {
        int id = 1;
        String expectedSQL = "SELECT * FROM " + tableName + " WHERE id = ?";

        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        TestEntity expectedEntity = new TestEntity(id, "data");

        when(connection.prepareStatement(expectedSQL)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(entityMapper.fromResultSet(rs)).thenReturn(expectedEntity);

        TestEntity result = repository.findById(id);

        assertNotNull(result);
        assertEquals(expectedEntity, result);
        verify(stmt).setObject(1, id);
    }

    @Test
    void testFindByIdReturnsNullOnException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);

        TestEntity result = repository.findById(1);

        assertNull(result);
    }

    @Test
    void testFindAllReturnsEntities() throws Exception {
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery("SELECT * FROM " + tableName)).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false); // two rows
        when(entityMapper.fromResultSet(rs)).thenReturn(
                new TestEntity(1, "one"),
                new TestEntity(2, "two")
        );

        List<TestEntity> result = repository.findAll();

        assertEquals(2, result.size());
        assertEquals("one", result.get(0).data());
        assertEquals("two", result.get(1).data());
    }

    @Test
    void testFindAllReturnsEmptyListOnException() throws Exception {
        when(connection.createStatement()).thenThrow(SQLException.class);

        List<TestEntity> result = repository.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testSaveReturnsEntity() throws Exception {
        TestEntity entity = new TestEntity(1, "save");

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(entityMapper.createInsertStatement(connection, tableName, entity)).thenReturn(stmt);

        TestEntity result = repository.save(entity);

        assertEquals(entity, result);
        verify(stmt).executeUpdate();
    }

    @Test
    void testSaveReturnsNullOnException() throws Exception {
        TestEntity entity = new TestEntity(1, "fail");
        when(entityMapper.createInsertStatement(connection, tableName, entity)).thenThrow(SQLException.class);

        TestEntity result = repository.save(entity);

        assertNull(result);
    }

    @Test
    void testUpdateReturnsEntity() throws Exception {
        TestEntity entity = new TestEntity(2, "update");

        PreparedStatement stmt = mock(PreparedStatement.class);
        when(entityMapper.createUpdateStatement(connection, tableName, entity)).thenReturn(stmt);

        TestEntity result = repository.update(entity);

        assertEquals(entity, result);
        verify(stmt).executeUpdate();
    }

    @Test
    void testUpdateReturnsNullOnException() throws Exception {
        TestEntity entity = new TestEntity(2, "failUpdate");
        when(entityMapper.createUpdateStatement(connection, tableName, entity)).thenThrow(SQLException.class);

        TestEntity result = repository.update(entity);

        assertNull(result);
    }

    @Test
    void testDeleteByIdSuccess() throws Exception {
        int id = 10;
        String expectedSQL = "DELETE FROM " + tableName + " WHERE id = ?";
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(connection.prepareStatement(expectedSQL)).thenReturn(stmt);

        repository.deleteById(id);

        verify(stmt).setObject(1, id);
        verify(stmt).executeUpdate();
    }

    @Test
    void testDeleteByIdHandlesException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);

        // Should not throw
        repository.deleteById(99);
    }

    // Example record entity for testing
    private record TestEntity(int id, String data) {}
}