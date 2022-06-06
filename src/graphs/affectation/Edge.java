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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((arete == null) ? 0 : arete.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Edge other = (Edge) obj;
        if (arete == null) {
            if (other.arete != null)
                return false;
        } else if (!arete.getExtremite1().equals(other.getTutored()))
            return false;
        else if (!arete.getExtremite2().equals(other.getTutor()))
            return false;
        return true;
    }
}
