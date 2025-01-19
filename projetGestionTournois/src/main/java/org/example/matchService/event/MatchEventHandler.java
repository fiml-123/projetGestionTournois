package org.example.matchService.event;

import org.example.classementService.service.ClassementService;
import org.example.matchService.model.Match;

public class MatchEventHandler {

    private final ClassementService classementService;

    public MatchEventHandler(ClassementService classementService) {
        this.classementService = classementService;
    }

    public void handle(MatchEvent event) {
        Object eventData = event.getData();
        if (eventData instanceof Match) {
            Match match = (Match) eventData;  // Cast sécurisé de 'data' en 'Match'
            switch (event.getType()) {
                case CREATE_MATCH:
                    // Organiser le match
                    System.out.println("Match Organisé: " + match.toString());
                    break;

                case DELETE_MATCH:
                    // Mettre à jour le classement lorsque le match est terminé
                    System.out.println("Match Terminé: " + match.toString());
                    // Mettre à jour le classement après que le match soit terminé
                    classementService.updateClassement(match);
                    break;

                case UPDATE_MATCH:
                    // Annuler le match
                    System.out.println("Match Annulé: " + match);
                    break;

                default:
                    System.out.println("Événement inconnu: " + event.getType());
            }
        } else {
            System.out.println("Erreur: Les données de l'événement ne sont pas de type Match.");
        }
    }
}
