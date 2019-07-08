package com.study.model;

import org.neo4j.ogm.annotation.*;

import java.util.List;

@NodeEntity(label = "user")
public class UserNeo4jModel {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "age")
    private Integer age;

    @Property(name = "sex")
    private String sex;

    @Relationship(type = "like")
    private List<UserNeo4jModel> userModels;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<UserNeo4jModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<UserNeo4jModel> userModels) {
        this.userModels = userModels;
    }
}
