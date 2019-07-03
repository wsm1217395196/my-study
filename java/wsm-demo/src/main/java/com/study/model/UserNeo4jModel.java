package com.study.model;

import org.neo4j.ogm.annotation.*;

@NodeEntity(label = "users")
public class UserNeo4jModel {

    @Id
    @GeneratedValue
    private Long nodeId;

    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "age")
    private Integer age;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
