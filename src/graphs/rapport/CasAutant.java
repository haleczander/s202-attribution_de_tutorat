package graphs.rapport;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe une affectation dans un cas où il y a un nombre égal de tutorés et de tuteurs
 */
public final class CasAutant {

    private CasAutant() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static void main(String[] args) {
        // liste des étudiants tutorés : extrait des données pour tester
        String[][] donneesTutores = new String[][] { { "Claude", "Allard", "9.8", "1" },
            { "Madeleine", "Barre", "6.9", "1" },
            { "Sabine", "Besnard", "12.7", "1" },
            { "Hugues", "Bigot", "0.2", "1" },
            { "Lucas", "Bouchet", "17.3", "1" },
            { "Alexandria", "Boulay", "12.5", "1" },
            { "Anouk", "Brun", "10.5", "1" },
            { "Hortense", "Chauveau", "9.1", "1" },
            { "David", "Colin", "7.0", "1" },
            { "Amélie", "Dijoux", "9.7", "1" } };

        // liste des étudiants tuteurs : extrait des données pour tester
        String[][] donneesTuteurs = new String[][] { { "Vincent", "1", "9.3", "2" },
            { "Jacqueline", "1", "13.2", "2" },
            { "Pénélope", "1", "13.2", "2" },
            { "Nicolas", "1", "13.1", "2" },
            { "Juliette", "1", "9.8", "2" },
            { "Sophie", "1", "15.8", "2" },
            { "Édouard", "1", "13.9", "3" },
            { "Olivier", "1", "11.3", "3" },
            { "Inès", "1", "9.3", "3" },
            { "Franck", "1", "11.9", "3" } };

        // création des listes d'étudiants
        List<Tutore> listeTutores = new ArrayList<>();
        List<Tuteur> listeTuteurs = new ArrayList<>();

        // on fou les étudiants dans une liste lol
        for (String[] strings : donneesTutores) {
            listeTutores.add(new Tutore(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3])));
        }

        // solution temporaire : pour les tuteurs, on remplace le nom par le nombre de tutorés qu'il peuvent prendre
        for (String[] strings : donneesTuteurs) {
            listeTuteurs.add(new Tuteur(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[1])));
        }

        Affectation affectation = new Affectation(listeTutores, listeTuteurs);
        System.out.println(affectation.obtenirAffectation(true) + "\n");
        System.out.println(affectation.obtenirCout() + "\n");

        // affectation: [Arete(David, Franck), Arete(Madeleine, Vincent), Arete(Hugues,
        // Pénélope), Arete(Amélie, Olivier), Arete(Alexandria, Inès), Arete(Claude,
        // Juliette), Arete(Lucas, Édouard), Arete(Sabine, Nicolas), Arete(Hortense,
        // Sophie), Arete(Anouk, Jacqueline)]

        // coût total: 5.637708383095427
    }
}
