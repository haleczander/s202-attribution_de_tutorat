package graphs.affectation;

import fr.ulille.but.sae2_02.graphes.Arete;
import oop.Student;
import oop.Tutor;
import oop.Tutored;

public class Edge {
    private Arete<? extends Student> arete;

    public Edge(Tutored tutored, Tutor tutor) {
        this.arete = new Arete<>(tutored, tutor);
    }

    public Tutored getTutored() {
        return (Tutored) arete.getExtremite1();
    }

    public Tutor getTutor() {
        return (Tutor) arete.getExtremite2();
    }
}
