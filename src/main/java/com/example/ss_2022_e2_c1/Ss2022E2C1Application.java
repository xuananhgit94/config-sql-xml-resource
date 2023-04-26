package com.example.ss_2022_e2_c1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class Ss2022E2C1Application {
    public static void main(String[] args) {
        var context = SpringApplication.run(Ss2022E2C1Application.class, args);
        SecurityFilterChain o = (SecurityFilterChain)context.getBean("securityFilterChain");
        for (var a : o.getFilters()) {
            System.out.println(a.getClass());
        }
    }
}
