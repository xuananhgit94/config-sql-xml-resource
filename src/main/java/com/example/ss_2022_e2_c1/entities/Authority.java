package com.example.ss_2022_e2_c1.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    private int id;
    private String name;
    private Set<User> users;
}
