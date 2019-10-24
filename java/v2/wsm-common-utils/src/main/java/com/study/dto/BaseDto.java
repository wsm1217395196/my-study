package com.study.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseDto {

    private Long id;
    /**
     *  名称
     */
    private String name;

}
