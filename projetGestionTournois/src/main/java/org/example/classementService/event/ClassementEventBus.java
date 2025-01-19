package org.example.classementService.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassementEventBus {

    private static final Map<ClassementEventType, List<ClassementEventHandler>> handlers = new HashMap<>();

    public static void subscribe(ClassementEventType eventType, ClassementEventHandler handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    public static void publish(ClassementEvent event) {
        List<ClassementEventHandler> eventHandlers = handlers.getOrDefault(event.getType(), new ArrayList<>());
        for (ClassementEventHandler handler : eventHandlers) {
            handler.handle(event);
        }
    }
}
