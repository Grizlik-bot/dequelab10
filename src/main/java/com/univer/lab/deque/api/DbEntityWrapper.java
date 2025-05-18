package com.univer.lab.deque.api;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.ArrayList;

@Data
@XmlRootElement(name = "entities")
@XmlAccessorType(XmlAccessType.FIELD)
public class DbEntityWrapper<E> {

    @XmlElement(name = "entity")
    private java.util.List<E> entities = new ArrayList<>();

    public java.util.List<E> getEntities() {
        return entities;
    }

    public void setEntities(java.util.List<E> entities) {
        this.entities = entities;
    }
}