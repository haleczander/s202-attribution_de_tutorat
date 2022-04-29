package graphs.rapport;

import java.util.HashMap;
import java.util.Map;

/**
 * Cas qui représente une affectation dans le cas ou il y a plus de tutorés que de tuteurs.
 * Explication de notre méthode au fur et à mesure en commentaire.
 */
public final class CasPlusDeTutores {

    private CasPlusDeTutores() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static void main(String[] args) {
        // tableau d'étudiants tutorés (extrait des données pour tester)
        String[][] data = new String[][] { { "Claude", "Allard", "9.8", "1" },
            { "Madeleine", "Barre", "6.9", "1" },
            { "Sabine", "Besnard", "12.7", "1" },
            { "Hugues", "Bigot", "0.2", "1" },
            { "Lucas", "Bouchet", "17.3", "1" },
            { "Alexandria", "Boulay", "12.5", "1" },
            { "Anouk", "Brun", "10.5", "1" },
            { "Hortense", "Chauveau", "9.1", "1" },
            { "David", "Colin", "7.0", "1" },
            { "Amélie", "Dijoux", "9.7", "1" },

            { "Vincent", "1", "9.3", "2" },
            { "Jacqueline", "1", "13.2", "2" },
            { "Pénélope", "1", "13.2", "2" },
            { "Nicolas", "1", "13.1", "2" },
            { "Juliette", "1", "9.8", "2" },
            { "Sophie", "2", "15.8", "2" },
            { "Édouard", "1", "13.9", "3" },
            { "Olivier", "2", "11.3", "3" },
            /*{ "Inès", "1", "9.3", "3" },
            { "Franck", "1", "11.9", "3" }*/ };

        Map<String,String> forcing = new HashMap<>();
        forcing.put("Claude", "Sophie");

        Assignment assignment = new Assignment(data, false, forcing);
        System.out.println(assignment.getTextAssignment(true) + "\n");
        System.out.println(assignment.getTextCost() + "\n");
    }
}
