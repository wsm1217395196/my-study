package com.study.controller;

import com.study.model.TestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestClass {

    public static void main(String[] args) {
        TestModel TestModel1 = new TestModel("wsm1", 20);
        TestModel TestModel2 = new TestModel("wsm2", 20);
        List<TestModel> list = new ArrayList<>();
        list.add(TestModel1);
        list.add(TestModel2);

        list.stream().filter(x -> !x.getName().contains("1")).collect(Collectors.toList()).forEach(System.out::println);
        list.forEach((x) -> System.err.println("name：" + x.getName()));
    }
}
