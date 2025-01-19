package org.example.notificationService.event;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NotificationEventBus {
    private static final List<Consumer<NotificationEvent>> subscribers = new ArrayList<>();
    private static PrintWriter serverWriter;

    public static void subscribe(Consumer<NotificationEvent> subscriber) {
        subscribers.add(subscriber);
    }

    public static void publish(NotificationEvent event) {
        System.out.println("Publish event: " + event.getMessage());
        for (Consumer<NotificationEvent> subscriber : subscribers) {
            subscriber.accept(event);
        }

        // Envoi au serveur de notification
        if (serverWriter != null) {
            serverWriter.println(event.getMessage());
        } else {
            System.out.println("Serveur de notification non connecté.");
        }
    }

    public static void connectToNotificationServer(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            serverWriter = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connecté au serveur de notification.");
        } catch (Exception e) {
            System.err.println("Erreur de connexion au serveur : " + e.getMessage());
        }
    }
}
