package com.univer.lab.deque;

import com.univer.lab.deque.api.TestEntity;
import com.univer.lab.deque.impl.XmlRepositoryImpl;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XmlRepositoryImplTest {

    private XmlRepositoryImpl<TestEntity, Integer> repository;
    private File xmlFile;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) throws JAXBException {
        xmlFile = tempDir.resolve("testEntities.xml").toFile();
        // Create a new repository for each test
        repository = new XmlRepositoryImpl<>(xmlFile, TestEntity.class, TestEntity::getId);
    }

    @Test
    void save_success() {
        TestEntity entity = new TestEntity(1, "Entity One");
        TestEntity saved = repository.save(entity);
        assertEquals(entity, saved, "Saved entity should be equal to the original");
        TestEntity found = repository.findById(1);
        assertEquals(entity, found, "findById should return the saved entity");
    }

    @Test
    void findById_success() {
        TestEntity entity1 = new TestEntity(1, "Entity One");
        TestEntity entity2 = new TestEntity(2, "Entity Two");
        repository.save(entity1);
        repository.save(entity2);

        TestEntity found = repository.findById(2);
        assertNotNull(found, "findById should return a non-null entity for an existing ID");
        assertEquals(entity2, found, "findById should return the correct entity based on ID");
    }

    @Test
    void findAll_success() {
        TestEntity entity1 = new TestEntity(1, "Entity One");
        TestEntity entity2 = new TestEntity(2, "Entity Two");
        repository.save(entity1);
        repository.save(entity2);

        var list = repository.findAll();
        assertEquals(2, list.size(), "findAll should return all saved entities");
        assertTrue(list.contains(entity1), "findAll result should contain entity1");
        assertTrue(list.contains(entity2), "findAll result should contain entity2");
    }

    @Test
    void update_success() {
        TestEntity entity = new TestEntity(1, "Entity One");
        repository.save(entity);

        TestEntity updatedEntity = new TestEntity(1, "Updated Entity One");
        TestEntity result = repository.update(updatedEntity);
        assertEquals(updatedEntity, result, "update should return the updated entity");

        TestEntity found = repository.findById(1);
        assertEquals(updatedEntity, found, "findById should return the updated entity");
    }

    @Test
    void deleteById_success() {
        TestEntity entity1 = new TestEntity(1, "Entity One");
        TestEntity entity2 = new TestEntity(2, "Entity Two");
        repository.save(entity1);
        repository.save(entity2);

        repository.deleteById(1);
        assertNull(repository.findById(1), "Entity with ID 1 should be deleted");
        List<TestEntity> list = repository.findAll();
        assertEquals(1, list.size(), "After deletion, findAll should return one entity");
        assertFalse(list.contains(entity1), "The list should not contain the deleted entity");
        assertTrue(list.contains(entity2), "The list should still contain the remaining entity");
    }
}