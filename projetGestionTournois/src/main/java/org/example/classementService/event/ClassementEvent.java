package org.example.classementService.event;

import java.util.Map;

public class ClassementEvent {

    private final ClassementEventType type;
    private final Map<String, Integer> classement;

    public ClassementEvent(ClassementEventType type, Map<String, Integer> classement) {
        this.type = type;
        this.classement = classement;
    }

    public ClassementEventType getType() {
        return type;
    }

    public Map<String, Integer> getClassement() {
        return classement;
    }
}
