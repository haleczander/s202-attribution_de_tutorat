package graphs.cas;

import graphs.rapport.Assignment;

/**
 * Cette classe test le comportement d'une affection lorsqu'il y a le même
 * nombre de tuteurs et de tutorés.
 */
public final class CasAutant {

    private CasAutant() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static void main(String[] args) {
        // list of students
        String[][] data = new String[][] {
                { "Claude", "Allard", "9.8", "1" },
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
                { "Sophie", "1", "15.8", "2" },
                { "Édouard", "1", "13.9", "3" },
                { "Olivier", "1", "11.3", "3" },
                { "Inès", "1", "9.3", "3" },
                { "Franck", "1", "11.9", "3" }
        };

        // on instancie une affectation à l'aide du tableau d'étudiants fourni.
        Assignment assignment = new Assignment(data);
        System.out.println(assignment.getTextAssignment(false) + "\n");
        System.out.println(assignment.getTextCost() + "\n");
    }
}
