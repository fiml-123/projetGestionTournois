package org.example.notificationService.server;

import org.example.notificationService.event.NotificationEvent;
import org.example.notificationService.event.NotificationEventBus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NotificationServer {
    private final int port;
    private final List<PrintWriter> clientWriters = new ArrayList<>();

    public NotificationServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Notification Server started on port " + port);

            // Subscribe to notification events
            NotificationEventBus.subscribe(this::broadcast);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected.");
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(writer);

                // Start a handler for each client to receive messages
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Message from client: " + message);

                // Broadcast the received message to all clients
                broadcast(new NotificationEvent(null, message));
            }
        } catch (Exception e) {
            System.out.println("Client disconnected.");
        }
    }

    private void broadcast(NotificationEvent event) {
        String message = event.getMessage();
        System.out.println("Broadcasting message: " + message);
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }

    public static void main(String[] args) {
        NotificationServer server = new NotificationServer(12345);
        server.start();
    }
}
