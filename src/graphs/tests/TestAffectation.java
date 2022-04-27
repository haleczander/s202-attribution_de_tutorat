package graphs.tests;

import java.util.ArrayList;
import java.util.List;

import graphs.rapport.Affectation;
import graphs.rapport.Tuteur;
import graphs.rapport.Tutore;

public final class TestAffectation {

    private TestAffectation() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static void main(String[] args) {
        String[][] testN = new String[][] { { "Claude", "Allard", "9.8", "1" },
            { "Madeleine", "Barre", "6.9", "1" },
            { "Sabine", "Besnard", "12.7", "1" },
            { "Hugues", "Bigot", "0.2", "1" },
            { "Lucas", "Bouchet", "17.3", "1" },
            { "Alexandria", "Boulay", "12.5", "1" },
            { "Anouk", "Brun", "10.5", "1" },
            { "Hortense", "Chauveau", "9.1", "1" },
            { "David", "Colin", "7.0", "1" },
            { "Amélie", "Dijoux", "9.7", "1" } };

        String[][] testF = new String[][] { { "Vincent", "1", "9.3", "2" },
            { "Jacqueline", "1", "13.2", "2" },
            { "Pénélope", "1", "13.2", "2" },
            { "Nicolas", "1", "13.1", "2" },
            { "Juliette", "1", "9.8", "2" },
            { "Sophie", "1", "15.8", "2" },
            { "Édouard", "1", "13.9", "3" },
            { "Olivier", "1", "11.3", "3" },
            { "Inès", "1", "9.3", "3" },
            { "Franck", "1", "11.9", "3" } };

        // listes d'étudiants
        List<Tutore> listeN = new ArrayList<>();
        List<Tuteur> listeF = new ArrayList<>();

        // on fou les étudiants dans une liste lol
        for (String[] strings : testN) {
            listeN.add(new Tutore(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3])));
        }

        // solution temporaire : pour les tuteurs on remplace le nom par nombre de tutorés qu'il peuvent prendre
        for (String[] strings : testF) {
            listeF.add(new Tuteur(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[1])));
        }

        Affectation a = new Affectation(listeN, listeF);
        System.out.println(a.obtenirAffectation(true) + "\n");
        System.out.println(a.obtenirCout() + "\n");
        System.out.println(Affectation.getNameList(listeN));
    }
}
