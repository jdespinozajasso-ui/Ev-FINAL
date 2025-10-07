package com.citas.util;


import java.util.Set;


public class IdGenerator {
    public static int nextId(Set<Integer> keys) {
        int id = 1;
        while (keys.contains(id)) id++;
        return id;
    }
}