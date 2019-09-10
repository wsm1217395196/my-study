package com.study.dto;

public class BaseDto {

    private Long id;
    /**
     *  名称
     */
    private String name;

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

    @Override
    public String toString() {
        return "BaseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
