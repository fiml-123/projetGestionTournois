package org.example.teamService.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private static final Map<TeamEventType, List<EventHandler>> handlers = new HashMap<>();

    // Méthode pour s'abonner à un type d'événement
    public static void subscribe(TeamEventType eventType, EventHandler handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    // Méthode pour publier un événement
    public static void publish(TeamEvent event) {
        List<EventHandler> eventHandlers = handlers.getOrDefault(event.getType(), new ArrayList<>());
        for (EventHandler handler : eventHandlers) {
            handler.handle(event);
        }
    }
}