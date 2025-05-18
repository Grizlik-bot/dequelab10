package com.univer.lab.deque.api;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "testEntity")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestEntity {
    private Integer id;
    private String name;
}
