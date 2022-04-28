package graphs.rapport;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

        // listes d'étudiants
        List<Tutore> listeTutores = new ArrayList<>();
        List<Tuteur> listeTuteurs = new ArrayList<>();

        // on fou les étudiants dans une liste lol
        for (String[] strings : donneesTutores) {
            listeTutores.add(new Tutore(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3])));
        }

        // solution temporaire : pour les tuteurs, on remplace le nom par le nombre de
        // tutorés qu'il peuvent prendre
        for (String[] strings : donneesTuteurs) {
            listeTuteurs.add(new Tuteur(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3]),
                    Integer.parseInt(strings[1])));
        }

        // deux problématiques qui nécessitent une liste d'attente :
        // - plus de tuteurs que de tutorés inscrits
        // - après potentiel split des tuteurs, il reste plus de tutorés que de tuteurs
        // disponibles

        // pour cet exemple : aucun tuteur ne pourra prendre plus d'un étudiant en
        // charge, car dans le programme final, cette partie ne sera exectuée que si
        // tous les étudiants qui s'occuper de plusieurs étudiants ont été split (et
        // donc leur attribut nbTutores == 1)
        // dans le groupe où il y a trop d'étudiants, faire un tri de cette liste et
        // retirer le nombre d'étudiants nécessaire
        // le tri sera arbitraire : nous avons décidé de le faire en fonction de l'année
        // puis de la moyenne (voir méthodes compareTo)
        int diff = listeTutores.size() - listeTuteurs.size();
        List<Etudiant> listeAttente = new ArrayList<>();

        if (diff > 0) {
            // trop de tutorés : on tri et on retire les étudiants les moins mauvais
            listeTutores.sort((e1, e2) -> e1.compareTo(e2));
            ListIterator<Tutore> it = listeTutores.listIterator(listeTutores.size());

            while (it.hasPrevious() && diff != 0) {
                Etudiant etu = it.previous();
                listeAttente.add(etu);
                diff--;
            }
            for (Etudiant etudiant : listeAttente) {
                listeTutores.remove(etudiant);
            }

            System.out.println(listeTutores);
            System.out.println(listeAttente + "\n");
        } else if (diff < 0) {
            // trop de tuteurs : on tri et on retire les étudiants les plus mauvais
            listeTuteurs.sort((e1, e2) -> e1.compareTo(e2));
            ListIterator<Tuteur> it = listeTuteurs.listIterator(listeTuteurs.size());

            while (it.hasPrevious() && diff != 0) {
                Etudiant etu = it.previous();
                listeAttente.add(etu);
                diff++;
            }
            for (Etudiant etudiant : listeAttente) {
                listeTuteurs.remove(etudiant);
            }

            System.out.println(listeTuteurs);
            System.out.println(listeAttente + "\n");
        }

        if (listeTutores.size() != listeTuteurs.size()) {
            throw new NullPointerException("Je sais pas c'que j'ai foutu mais ça marche pas");
        }

        // TODO : refactor cette merde
        // avec des ? extends Etudiant et des instanceOf pour faire un traitement différenciés

        // maintenant on a des belles listes on peut affecter :)
        Affectation affectation = new Affectation(listeTutores, listeTuteurs);
        System.out.println("affectation: " + affectation.obtenirAffectation(true) + "\n");
        System.out.println("coût total: " + affectation.obtenirCout() + "\n");

        if (!listeAttente.isEmpty()) {
            System.out.println("liste d'attente: " + listeAttente);
        } else {
            System.out.println("aucun étudiant en liste d'attente");
        }
    }
}
