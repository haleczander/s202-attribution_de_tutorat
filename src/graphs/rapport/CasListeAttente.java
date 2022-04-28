package graphs.rapport;

/**
 * Cette classe représente un cas où un ou plusieurs étudiants doivent être mis
 * en liste d'attente (pour ne pas dire virés).
 * Explication de notre méthode au fur et à mesure en commentaire.
 */
public final class CasListeAttente {

    private CasListeAttente() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static void main(String[] args) {
        // liste des étudiants tutorés : extrait des données pour tester
        String[][] data = new String[][] { { "Claude", "Allard", "9.8", "1" },
                { "Madeleine", "Barre", "6.9", "1" },
                { "Sabine", "Besnard", "12.7", "1" },
                { "Hugues", "Bigot", "0.2", "1" },
                { "Lucas", "Bouchet", "17.3", "1" },
                { "Alexandria", "Boulay", "12.5", "1" },
                { "Anouk", "Brun", "10.5", "1" },
                { "Hortense", "Chauveau", "9.1", "1" },
                { "David", "Colin", "7.0", "1" },
                /*{ "Amélie", "Dijoux", "9.7", "1" },*/

                { "Vincent", "1", "9.3", "2" },
                { "Jacqueline", "1", "13.2", "2" },
                { "Pénélope", "1", "13.2", "2" },
                { "Nicolas", "1", "13.1", "2" },
                { "Juliette", "1", "9.8", "2" },
                { "Sophie", "1", "15.8", "2" },
                { "Édouard", "1", "13.9", "3" },
                { "Olivier", "1", "11.3", "3" },
                { "Inès", "1", "9.3", "3" },
                { "Franck", "1", "11.9", "3" } };

        
        Assignment assignment = new Assignment(data);
        System.out.println("affectation: " + assignment.getTextAffectation(true) + "\n");
        System.out.println("coût total: " + assignment.getTextCout() + "\n");
    }
}
