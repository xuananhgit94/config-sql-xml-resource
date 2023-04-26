package com.example.ss_2022_e2_c1.exception;

import java.lang.reflect.Field;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class MessageFactory {
    public static String ERROR1 = "ERROR1";
    public static String ERROR2 = "ERROR2";

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("error");
        Class<?> clazz = MessageFactory.class;
        Field[] fields =  clazz.getFields();
        Stream.of(fields).forEach(f -> {
            try {
            f.set(f.getName(), resourceBundle.getString((String)f.get(f.getName())));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
