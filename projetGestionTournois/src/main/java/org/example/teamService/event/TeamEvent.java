package org.example.teamService.event;


public class TeamEvent {
    private TeamEventType type;
    private Object data;

    public TeamEvent(TeamEventType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public TeamEventType getType() { return type; }
    public Object getData() { return data; }
}
