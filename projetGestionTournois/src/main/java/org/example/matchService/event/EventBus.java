package org.example.matchService.event;


import org.example.classementService.event.ClassementEvent;
import org.example.classementService.event.ClassementEventHandler;
import org.example.classementService.event.ClassementEventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private static final Map<MatchEventType, List<MatchEventHandler>> handlers = new HashMap<>();

    public static void subscribe(MatchEventType eventType, MatchEventHandler handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    public static void publish(MatchEvent event) {
        List<MatchEventHandler> eventHandlers = handlers.getOrDefault(event.getType(), new ArrayList<>());
        for (MatchEventHandler handler : eventHandlers) {
            handler.handle(event);
        }
    }
}
