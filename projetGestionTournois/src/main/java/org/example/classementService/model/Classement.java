package org.example.classementService.model;

import java.util.Map;

public class Classement {

    private final Map<String, Integer> classement;

    public Classement(Map<String, Integer> classement) {
        this.classement = classement;
    }

    public Map<String, Integer> getClassement() {
        return classement;
    }
}
