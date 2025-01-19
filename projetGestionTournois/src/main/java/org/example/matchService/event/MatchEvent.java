package org.example.matchService.event;

public class MatchEvent {
    private MatchEventType type;
    private Object data;

    public MatchEvent(MatchEventType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public MatchEventType getType() { return type; }
    public Object getData() { return data; }
}
