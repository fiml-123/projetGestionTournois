package org.example.notificationService.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NotificationClientHandler implements Runnable {
    private final Socket clientSocket;
    private final PrintWriter writer;

    public NotificationClientHandler(Socket clientSocket, PrintWriter writer) {
        this.clientSocket = clientSocket;
        this.writer = writer;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Message from client: " + message);
            }
        } catch (Exception e) {
            System.out.println("Client disconnected.");
        }
    }
}
