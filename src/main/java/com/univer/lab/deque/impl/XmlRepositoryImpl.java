package com.univer.lab.deque.impl;

import com.univer.lab.deque.api.DbEntityWrapper;
import com.univer.lab.deque.api.DbRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class XmlRepositoryImpl<ENTITY, ID> implements DbRepository<ENTITY, ID> {

    private final File xmlFile;
    private final Class<ENTITY> entityClass;
    private final Function<ENTITY, ID> idExtractor;
    private final JAXBContext jaxbContext;

    /**
     * Constructor.
     *
     * @param xmlFile the file used to store data
     * @param entityClass the class of the entity
     * @param idExtractor function to extract the identifier from the entity
     * @throws JAXBException if JAXBContext cannot be created
     */
    public XmlRepositoryImpl(File xmlFile, Class<ENTITY> entityClass, Function<ENTITY, ID> idExtractor) throws JAXBException {
        this.xmlFile = xmlFile;
        this.entityClass = entityClass;
        this.idExtractor = idExtractor;
        // Create a JAXBContext for the wrapper and the entity class
        this.jaxbContext = JAXBContext.newInstance(DbEntityWrapper.class, entityClass);
    }

    @Override
    public ENTITY findById(ID id) {
        List<ENTITY> list = findAll();
        Optional<ENTITY> found = list.stream()
                                     .filter(entity -> idExtractor.apply(entity).equals(id))
                                     .findFirst();
        return found.orElse(null);
    }

    @Override
    public List<ENTITY> findAll() {
        DbEntityWrapper<ENTITY> wrapper = loadData();
        return wrapper.getEntities();
    }

    @Override
    public ENTITY save(ENTITY entity) {
        List<ENTITY> list = findAll();
        // Check that an entity with the same ID does not already exist
        if (list.stream().anyMatch(e -> idExtractor.apply(e).equals(idExtractor.apply(entity)))) {
            throw new IllegalArgumentException("Entity with the same ID already exists.");
        }
        list.add(entity);
        saveData(list);
        return entity;
    }

    @Override
    public ENTITY update(ENTITY entity) {
        List<ENTITY> list = findAll();
        ID id = idExtractor.apply(entity);
        for (int i = 0; i < list.size(); i++) {
            if (idExtractor.apply(list.get(i)).equals(id)) {
                list.set(i, entity);
                saveData(list);
                return entity;
            }
        }
        throw new IllegalArgumentException("Entity with ID " + id + " not found.");
    }

    @Override
    public void deleteById(ID id) {
        List<ENTITY> list = findAll();
        boolean removed = list.removeIf(entity -> idExtractor.apply(entity).equals(id));
        if (removed) {
            saveData(list);
        }
    }

    // Load data from XML file
    @SuppressWarnings("unchecked")
    private DbEntityWrapper<ENTITY> loadData() {
        try {
            if (!xmlFile.exists() || xmlFile.length() == 0) {
                // If the file does not exist or is empty, return an empty wrapper
                return new DbEntityWrapper<>();
            }
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (DbEntityWrapper<ENTITY>) unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException("Error loading data from XML", e);
        }
    }

    // Save data to XML file
    private void saveData(List<ENTITY> entities) {
        DbEntityWrapper<ENTITY> wrapper = new DbEntityWrapper<>();
        wrapper.setEntities(entities);
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(wrapper, xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException("Error saving data to XML", e);
        }
    }
}