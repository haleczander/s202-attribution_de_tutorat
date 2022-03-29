package graphs.tests;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public class Salut {
    public static void main(String[] args) {
        // étudiants à aider (partieN)
        String[][] partieN = new String[][]{{"Jean","8.63","1"},{"Harry","7.97","1"},{"Paul","10.26","1"},{"Timothé","9.81","1"}};

        // étudiants aideurs (partieF)
        String[][] partieF = new String[][]{{"Antoine","13.64","3"},{"Romain","14.12","3"},{"Florence","16.39","2"},{"Camille","14.54","2"}};

        // on les fout dans des listes pour plus tard (trust me)
        List<String> partieNList = new ArrayList<>();
        for (String[] strings : partieN) {
            partieNList.add(strings[0]);
        }

        List<String> partieFList = new ArrayList<>();
        for (String[] strings : partieF) {
            partieFList.add(strings[0]);
        }

        // nouveau graph
        GrapheNonOrienteValue<String> graph = new GrapheNonOrienteValue<>();

        // on ajoute tous les sommets (utiliser la liste à la place plus tard)
        for (String[] strings : partieN) {
            graph.ajouterSommet(strings[0]);
        }
        for (String[] strings : partieF) {
            graph.ajouterSommet(strings[0]);
        }

        // on fait un savant calcul pour faire les arêtes
        double calculN, calculF, total;
        for (String[] stringsN : partieN) {
            for (String[] stringsF : partieF) {
                calculN = Double.parseDouble(stringsN[1]) * Integer.parseInt(stringsN[2]);
                calculF = Double.parseDouble(stringsF[1]) * Integer.parseInt(stringsF[2]);
                total = calculF - calculN;
                graph.ajouterArete(stringsN[0], stringsF[0], total);
            }
        }

        // pour check
        System.out.println(graph.toString() + "\n");

        // on fait une affectation
        CalculAffectation<String> affectation = new CalculAffectation<>(graph, partieNList, partieFList);

        // pour check
        System.out.println(affectation.getAffectation() + "\n");
        System.out.println(affectation.getCout());
    }

    // faire un classement : partieN = plus mauvais en premier et partieF = meilleur en premier
    // poids des arêtes = classement partieF - classement partieN
}
