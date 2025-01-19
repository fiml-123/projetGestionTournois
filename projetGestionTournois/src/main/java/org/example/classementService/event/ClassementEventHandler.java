package org.example.classementService.event;

public class ClassementEventHandler {

    public void handle(ClassementEvent event) {
        switch (event.getType()) {
            case ClassementMisAJour:
                System.out.println("=== Classement Mis à Jour ===");
                event.getClassement().forEach((team, points) -> {
                    System.out.println(team + ": " + points + " points");
                });
                break;

            default:
                System.out.println("Type d'événement inconnu : " + event.getType());
        }
    }
}
