package org.example.notificationService.event;

public class NotificationEvent {
    private final NotificationEventType type;
    private final String message;

    public NotificationEvent(NotificationEventType type, String message) {
        this.type = type;
        this.message = message;
    }

    public NotificationEventType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}
