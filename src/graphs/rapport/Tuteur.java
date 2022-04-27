package graphs.rapport;

public class Tuteur extends Etudiant {
    public int nbTutores;

    public Tuteur(String name, double average, int level, int nbTutores) {
        super(name, average, level);
        this.nbTutores = nbTutores;
    }

    @Override
    public String toString() {
        return super.toString() + " " + nbTutores;
    }
}
