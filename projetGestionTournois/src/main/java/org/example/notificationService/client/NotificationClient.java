package org.example.notificationService.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NotificationClient {
    private final String serverAddress;
    private final int port;

    public NotificationClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            System.out.println("Connected to notification server.");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Notification received: " + message);
            }
        } catch (Exception e) {
            System.out.println("Disconnected from server.");
        }
    }

    public static void main(String[] args) {
        NotificationClient client = new NotificationClient("localhost", 12345);
        client.start();
    }


}
